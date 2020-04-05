
function swim(max,use) {
	var waveNum=parseInt(100-(use/max)*100);
	if (waveNum<0){
		waveNum=0;
	}
	apps.Percentage=waveNum+"%";
	element.progress('demo', apps.Percentage);
}

function getToken(variable){
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i=0;i<vars.length;i++) {
		var pair = vars[i].split("=");
		if(pair[0] == variable){return pair[1];}
	}
	return("false");
}
//获取url
function TrojanUrl(){
	var url= SERVER_ADDR +"/TrojanUrl?_="+randomNum()+"&token="+apps.token+"&user="+apps.user;
	$.getJSON(url,function (data) {
		apps.trojan_url=data.url;
		apps.url_type=data.type;
		apps.bandwidth=data.bandwidth;
		apps.serveraddr=data.address;
		apps.clash=data.clash;
	});
}
//重置url
function rseUrl() {
	layer.open({
		content: '如果您觉得自己的URL已经被他人利用，建议您通过此方式重置您的URL链接。' +
			'注意，重置后原URL会失效，您需要复制新的URL到您的代理工具'
		,btn: ['重置', '取消']
		,yes: function(index){
			ResUrl();
			layer.close(index);
		}
	});
}
function ResUrl() {
	layer.open({
		content: resUrlHtml,
		btn: '重置',
		shadeClose: false,
		yes: function(){
			toResUrl();
			return 0;
		}
	});
}

function toResUrl() {
	layer.open({type: 2,time:2,content:"交互中"});
	let pwd=$('#resPwd').val();
	console.log("用户名="+apps.user+"密码="+pwd);
	let url=SERVER_ADDR+"/resUrl";
	$.post(url,{
		user:apps.user,
		pwd:pwd,
	},function (data) {
		data=JSON.parse(data);
		if (data.message=='yes'){
			layer.open({
				content: '重置成功,请复制新的URL以使用'
				,btn: '确定'
				,yes:function () {
					location.href='';
				}
			});
		}else {
			layer.open({
				title: '错误'
				,content: '密码错误'
			});
		}
	})
}
//获取流量综合
function getQuate() {
	var url=SERVER_ADDR+"/used?_="+randomNum()+"&type=mine&token="+apps.token+"&user="+apps.user;
	$.getJSON(url,function (date) {
		apps.used_old=date.used;
		apps.max=date.max;
		swim(date.max,date.used);
	})
}
//获取历史流量
function getQuateall() {
	var url=SERVER_ADDR+"/used?_="+randomNum()+"&type=all&token="+apps.token+"&user="+apps.user;
	$.getJSON(url,function (date) {
		apps.useds=date.useds;
		apps.times=date.times;
		if (date.useds==null){
			$('#leave').hide();
			clearInterval(runB);
			runB=null;
			logining=false;
			login();
		}else if (runB==null){
			$('#leave').show(300);
			$(".home").eq(0).slideDown(300);
			logining=true;
			get_all();
			return true;
		}
	})
}
var layboxIndex;
function login() {
			layboxIndex=layer.open({
				content: loginhtml,
				btn: '登录',
				shadeClose: false,
				yes: function(){
					to_login();
					return 0;
				}
			});
	if (apps.token!=''){
		apps.loginMessage='登录已过期，请重新登陆';
	}
	$("#loginMessage").html(apps.loginMessage);
}

function findPassword() {
	layer.open({
		content: findPasswordHtml,
		shadeClose: false,
	});
	$('#provingBox').hide();
	$('#findBox').hide();
}

function toFindUser() {
	layer.open({type: 2,time:2,content:"交互中"});
	let user=$('#putname').val();
	if (user!=''){
		let url=SERVER_ADDR+"/getQuestionServlet?_="+randomNum()+"&user="+user;
		$.getJSON(url,function (data) {
			if (data.error=='404'){
				layer.open({
					title: '错误'
					,content: '用户未找到或未设置安全问题'
					,end:function () {
						findPassword();
					}
				});
			}else {
				$('#findUserBox').hide();
				$('#yourQuestion').html("问题:"+data.question+"?");
				$('#provingBox').show();
			}
		})
	}else {
		layer.open({
			title: '错误'
			,content: '用户名不能为空'
			,end:function () {
				findPassword();
			}
		});
	}

}
function getFindToken() {
	layer.open({type: 2,time:2,content:"交互中"});
	let user=$('#putname').val();
	let ans=$('#putans').val();
	if (ans!=''){
		let url=SERVER_ADDR+"/getFindToken?_="+randomNum()+"&user="+user+"&ans="+ans;
		$.getJSON(url,function (data) {
			if (data.error=='false'){
				layer.open({
					title: '错误'
					,content: '安全问题答案错误'
					,end:function () {
						findPassword();
					}
				});
			}else {
				findTimeout();
				apps.token=data.token;
				$('#provingBox').hide();
				$('#findBox').show();
			}
		})
	}else {
		layer.open({
			title: '错误'
			,content: '答案不能为空'
			,end:function () {
				findPassword();
			}
		});
	}
}

function toFindPassword() {
	layer.open({type: 2,time:2,content:"交互中"});
	let user=$('#putname').val();
	let ans=$('#putans').val();
	let pwd=$('#Fpwd').val();
	let pwd2=$('#Fpwd2').val();
	if (pwd!=pwd2 || pwd2=='' ||pwd==''){
		layer.open({
			title: '错误'
			,content: '两个密码不一致或者都为空'
			,end:function () {
				findPassword();
				$('#provingBox').hide();
				$('#findUserBox').hide();
				$('#findBox').show();
				$('#putname').val(user);
				$('#putans').val(ans);
			}
		});
	}else {
		let url=SERVER_ADDR+"/findPwd";
		$.post(url,{
			user:user,
			pwd:pwd,
			token:apps.token
		},function (data) {
			data=JSON.parse(data);
			if (data.message=='yes'){
				layer.open({
					title: '成功'
					,content: '您已成功修改密码,即将跳转'
					,time:2
					,end:function () {
						location.href="";
					}
				});
			}else if (data.message=='token error'){
				layer.open({
					title: '失败'
					,content: '您的操作超时,请重新验证'
					,end:function () {
						findPassword();
					}
				});
			}else {
				layer.open({
					title: '失败'
					,content: '服务器参数错误'
					,end:function () {
						findPassword();
					}
				});
			}
		});
	}
}

function regist() {

	layer.close(layboxIndex);
	layboxIndex=layer.open({
		content: registHtml,
		btn: '注册',
		shadeClose: false,
		yes: function(){
			toRegist();
			return 0;
		}
	});
	//注册姓名保留
	if (apps.user!='' || apps.user!=null){
		$('#Rusername').val(apps.user);
	}
}

function toRegist() {
	layer.open({type: 2,time:2,content:"通信中"});
	let pwd=$('#Rpwd').val();
	let pwd2=$('#Rpwd2').val();
	let uname=$('#Rusername').val();
	let question=$("#questions").val();
	let ans=$("#ans").val();
	let company=$('#company').val();
		if (pwd=="" || pwd2=="" ||uname=="" || company=="" || question=="" || ans==""){
			layer.open({
				title: '错误'
				,content: '检查是不是有没填上的选项呢'
				,end:function () {
					regist();
				}
			});
			apps.user=uname;
		}else if(pwd!=pwd2){
			layer.open({
				title: '错误'
				,content: '两个密码不一致!'
				,end:function () {
					regist();
				}
			});
			apps.user=uname;
		}else {
			var url=SERVER_ADDR+"/registS";
			$.post(url,{
				user:uname,
				pwd:pwd,
				company:company,
				question:question,
				ans:ans
			},function (data) {
				if (data.message=="注册成功"){
					this.token=data.token;
					layer.open({
						title: 'yes'
						,btn:'确定'
						,content: '注册成功，单击确定跳转到登陆页面',
						yes:function () {
							location.href="";
						}
					});
					apps.token='';
				}else {
					layer.open({
						title: '错误'
						,content: data.message
						,end:function () {
							regist();
						}
					});
					apps.user=uname;
				}
			},"json")
		}
}
function leave() {
	localStorage.setItem("token",'');
	localStorage.setItem("user",'');
	location.href="";
}

//cdk充值
function add() {
	if (apps.cdk==''){
		layer.open({
			title: '错误'
			,content: '请输入cdk'
		});
		return 0;
	}
	layer.open({type: 2,time:1,content:"交易中"});
	var url=SERVER_ADDR+"/addQuotaServlet?_="+randomNum()+"&user="+apps.user+"&cdk="+apps.cdk;
	var message='';
	$.getJSON(url,function (data) {
		message=data.message;
			if (message == "cdk_is_false") {
				layer.open({
					title: '错误'
					, content: '该cdk无效'
				});
			} else if (message == "full") {
				layer.open({
					title: '失败'
					, content: '您已经无限流量了，请把充值码留给他人用哈'
				});
			} else if (message >= 0.1 && message <= 1024) {
				layer.open({
					title: '充值成功'
					, content: '您成功充值了' + message + ' GB 流量(有效期至次月1日)，敬请体验吧！'
				});
				apps.cdk='';
			} else {
				layer.open({
					title: '错误'
					, content: '服务器参数错误，请稍后重试'
				});
			}
		});
}

function setQuestion() {
	layer.open({
		content: setQuestionHtml,
		btn: '提交',
		shadeClose: false,
		yes: function(){
			toSetQuestion();
			return 0;
		}
	});
}

function toSetQuestion() {

	let question=$("#SETquestions").val();
	let ans=$("#SETans").val();
	if (question=='' || ans==''){
		layer.open({
			title: '错误'
			,content: '请选择问题或添加回答'
			,end:function () {
				setQuestion();
			}
		});
		return 0;
	}
	console.log("用户名="+apps.user+"密码"+apps.pwd+"问题"+question+"答案"+ans)
	layer.open({type: 2,time:2,content:"交互中"});
	var url=SERVER_ADDR+"/setQuestionS";
	$.post(url,{
		user:apps.user,
		pwd:apps.pwd,
		question:question,
		ans:ans
	},function (data) {
		console.log("返回"+data)
		data=JSON.parse(data);
		console.log("转对象"+data.message)
		if (data.message=='pwd error'){
			layer.open({
				title: '错误'
				,content: '密码验证错误'
				,end:function () {
					toSetQuestion();
				}
			});
			return 0;
		}else if(data.message=='yes'){
			layer.open({
				title: '成功'
				,content: '您已成功设置安全问题,即将跳转'
				,time:2
				,end:function () {
					localStorage.setItem("token",apps.token);
					localStorage.setItem("user",apps.user);
					TrojanUrl();
					getQuateall();
					getQuate();
					location.href="";
				}
			});
			return 0;
		}else {
			layer.open({
				title: '错误'
				,content: '服务器参数错误'
				,end:function () {
					toSetQuestion();
				}
			});
			return 0;
		}
	})
}
function to_login() {
	var user=document.getElementById('username').value;
	var pwd=document.getElementById('pwd').value;
	// alert(user)
	if (user=='' || pwd==''){
			layer.open({
				title: '错误'
				,content: '用户名或密码不能为空'
				,end:function () {
					login();
				}
			});
		return 0;
	}
	layer.open({type: 2,time:2,content:"登录中"});
	var url=SERVER_ADDR+"/Logins";
	$.post(url,{
		user:user,
		pwd:pwd
	},function (data) {
		if (data.token!=null){
			layer.open({
				shadeClose:false,
				content: '登陆成功'
				,style: 'background-color:#09C1FF; color:#fff; border:none;' //自定风格
				,time: 1
			});
			layer.close(layboxIndex);
			if (data.message=='no question'){
				apps.token=data.token;
				apps.user=user;
				apps.pwd=pwd;
				setQuestion();
			}else {
				apps.token=data.token;
				apps.user=user;
				// 更新缓存
				localStorage.setItem("token",apps.token);
				localStorage.setItem("user",user);
				TrojanUrl();
				getQuateall();
				getQuate();
				location.href="";
			}
			return 1;
		}else {
			layer.open({
				title: '错误'
				,content: '用户名或密码错误'
				,end:function () {
					login();
				}
			});
			return 0;
		}
	},"json")

}
//判断UA
function UA() {
	var os = function (){
		var ua = navigator.userAgent,
			isWindowsPhone = /(?:Windows Phone)/.test(ua),
			isSymbian = /(?:SymbianOS)/.test(ua) || isWindowsPhone,
			isAndroid = /(?:Android)/.test(ua),
			isFireFox = /(?:Firefox)/.test(ua),
			isChrome = /(?:Chrome|CriOS)/.test(ua),
			isTablet = /(?:iPad|PlayBook)/.test(ua) || (isAndroid && !/(?:Mobile)/.test(ua)) || (isFireFox && /(?:Tablet)/.test(ua)),
			isPhone = /(?:iPhone)/.test(ua) && !isTablet,
			isPc = !isPhone && !isAndroid && !isSymbian;
		return {
			isTablet: isTablet,
			isPhone: isPhone,
			isAndroid: isAndroid,
			isPc: isPc
		};
	}();

	if (os.isAndroid || os.isPhone) {
		return true;
	} else if (os.isTablet) {
		return false;
	} else if(os.isPc) {
		return false;
	}
}

function copyUrl(num) {
	layer.open({type: 2,time:1,content:"加载中"});
	layer.open({
		content: copyUrlValue,
		btn: '复制URL',
		shadeClose: true,
		yes: function(){
				var copyText = $("#URL");//获取对象
				copyText.select();//选择
				document.execCommand("Copy");//执行复制
				// alert("复制成功！");

			layer.open({
				shadeClose:false,
				content: '复制成功，请粘贴到点火器使用'
				,style: 'background-color:#09C1FF; color:#fff; border:none;' //自定风格
				,time: 3
			});
				layer.close(layboxIndex);
			return 0;
		}
	});
	if (num==-1){
		$('#URL').html(apps.clash);
	}else {
		$('#URL').html(apps.trojan_url[num]);
	}
}

function randomNum(){
	return less(100000,999999);
	function less(minNum,maxNum) {
		switch(arguments.length){
			case 1:
				return parseInt(Math.random()*minNum+1,10);
				break;
			case 2:
				return parseInt(Math.random()*(maxNum-minNum+1)+minNum,10);
				break;
			default:
				return 0;
				break;
		}
	}
}

function getNotice() {
	var url=SERVER_ADDR+"/NoticeServlet?_="+randomNum();
	$.getJSON(url,function (data) {
		layer.open({
			content: Notice,
			btn: '我知道了',
			shadeClose: false,
			yes: function(){
				layer.open({
					content: '感谢理解'
					,time: 2
					,skin: 'msg'
				});
			}
		});
		$('#Notice').html(data.notice);
	});
}

function findTimeout() {
	var findPwdTimeOut=0;
	var timeoutRun=null;
	findPwdTimeOut=300;
	timeoutRun=setInterval(function () {
		findPwdTimeOut--;
		$('#timeout').html(findPwdTimeOut);
		if (findPwdTimeOut==0){
			clearInterval(timeoutRun);
			timeoutRun=null;
		}
	},1000);
}