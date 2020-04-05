function load(){
    layer.open({type: 2,time:1,content:"加载中"});
    $('#leave').hide();
    $(".lists").hide();
    $(".home").hide();

    $("#more").click(function () {
            $(".lists").slideToggle(300);
        }
    );

    $(".list").click(function () {
        $(".lists").slideUp(300);
        var index=$(".list").index(this);
        $(".home").eq(index).slideDown(300);
        layer.open({type: 2,time:1,content:"加载中",end:function () {
            f();
           }});
        $(".home").eq(index).siblings().slideUp(300);
    });


    apps=new Vue({
        el:"#apps",
        data:{
            pwd:'',
            clash:'',
            loginMessage:'',
            copy_url:'',
            Percentage:'',
            trojan_url:[],
            url_type:[],
            bandwidth:[],
            serveraddr:[],
            pass:'',
            user:'',
            token:'',

            used_old:'',
            max:'',

            useds:[],
            times:[],
            cdk:''
        },
        methods:{
            today:function(){
                var today=0;
                for (i in apps.useds){
                    today+=parseFloat(apps.useds[i]);
                }
                return (apps.used_old-today).toFixed(3)+" GB";
            },
            getUrl:function () {
                TrojanUrl();
            },
            getQuates:function () {
                getQuate();
            },
            getQuatesall:function () {
                getQuateall();
            },
            cdkadd:function () {
                add();
            },
            go_login:function(){
                to_login();
            },
            Quate_used:function () {
                var m=''
                if (apps.used_old>apps.max){
                    m= " 流量用尽，请及时充值";
                }
                return m;
            },
            haveQuate:function () {
                if (apps.used_old>apps.max){
                    return "0";
                }else {
                    return (apps.max-apps.used_old).toFixed(2);
                }
            }
        }
    });
    apps.token=localStorage.getItem("token");
    apps.user=localStorage.getItem("user");
    if (apps.token!=''){
        apps.loginMessage='登录已过期，请重新登陆';
    }
    getQuateall();
    getQuate();
    TrojanUrl();
}

var runB=null;

function get_all() {
    getNotice();
    runB=setInterval(function () {
        getQuateall();
        getQuate();
    },5000);
}

function f() {

    $(".ccccc").click(function () {
        copyUrl($(".ccccc").index(this));
    });
    $(".clash_button").click(function () {
        copyUrl(-1);
    });

}