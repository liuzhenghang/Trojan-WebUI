var SERVER_ADDR="http://www.www.www:80";
// 后端接口地址
var loginhtml="    <h3 id='loginMessage'></h3>\n" +
    "<div class='layui-form-item'>\n" +
    "       <label class='layui-form-label'>用户名</label>\n" +
    "       <div class='layui-input-block'>\n" +
    "               <input id='username' type='text' name='title' autocomplete='off' placeholder='请输入用户名' class='layui-input'>\n" +
    "            </div>\n" +
    "    </div>\n" +
    "<div class='layui-form-item'>\n" +
    " <label class='layui-form-label'>密码</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='pwd' type='password' name='title' autocomplete='off' placeholder='请输入密码' class='layui-input'>\n" +
    "        </div>\n" +
    "    </div>" +
    "<a class='loginAndRegist'  onclick='findPassword()'>我忘记了密码</a>" +
    "<br><br>" +
    "<a class='loginAndRegist'  onclick='regist()'>没有账号？注册一个</a>";

var registHtml="<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>用户名</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='Rusername' type='text' name='title' autocomplete='off' placeholder='请输入用户名' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>\n" +
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>密码</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='Rpwd' type='password' name='title' autocomplete='off' placeholder='请输入密码' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>\n" +
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>密码</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='Rpwd2' type='password' name='title' autocomplete='off' placeholder='请再次输入密码' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>\n" +
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>邀请码</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='company' type='text' name='title' autocomplete='off' placeholder='请输入邀请码' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>" +
    "<div id='question' class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>设置安全问题</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <select id='questions' lay-verify='required'>\n" +
    "            <option value=''>请选择一个问题</option>\n" +
    "            <option value='你QQ号多少'>你QQ号多少</option>\n" +
    "            <option value='你的小学名字是什么'>你的小学名字是什么</option>\n" +
    "            <option value='你的初中名字叫什么'>你的初中名字叫什么</option>\n" +
    "            <option value='你的配偶叫什么'>你的配偶叫什么</option>\n" +
    "            <option value='你的第一部手机是什么牌子的'>你的第一部手机是什么牌子的</option>\n" +
    "        </select>\n" +
    "    </div>\n" +
    "</div>\n" +
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>你的答案</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='ans' type='text' name='title' autocomplete='off' placeholder='请输入答案' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>" +
    "<a class='loginAndRegist' onclick='login()'>转到登录</a>";

var copyUrlValue="<h2>URL</h2>" +
    "<textarea id=\"URL\" readonly='readonly' class=\"layui-textarea\"></textarea>";
var Notice="<h1 class='Notice'>公告</h1>" +
    "<div id='Notice'></div>";

var findPasswordHtml=
    "<div id='findUserBox'>"+
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>用户名</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='putname' type='text' name='title' autocomplete='off' placeholder='请输入用户名' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>\n" +
    "<button class='layui-btn layui-btn-normal' onclick='toFindUser()'>验证用户名</button>" +
    "</div>" +
    "<div id='provingBox'>" +
    "<p>请验证你的安全问题</p>" +
    "<h2 id='yourQuestion'>问题:</h2>" +
    "<p id='getQuestion'></p>" +
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>答案</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='putans' type='text' name='title' autocomplete='off' placeholder='请输入答案' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>\n" +
    "<button  class='layui-btn layui-btn-normal' onclick='getFindToken()'>验证答案</button>" +
    "</div>" +
    "<div id='findBox'>" +
    "<p>请设置密码，您还有<a id='timeout'></a>秒的时间</p>" +
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>密码</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='Fpwd' type='password' name='title' autocomplete='off' placeholder='请输入密码' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>\n" +
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>密码</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='Fpwd2' type='password' name='title' autocomplete='off' placeholder='请再次输入密码' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>\n" +
    "<button  class='layui-btn layui-btn-normal' onclick='toFindPassword()'>确认修改</button>" +
    "</div>";


var setQuestionHtml=
    "<h5>您还没有设置安全问题</h5>" +
    "<h5 style='color: red'>安全问题仅用于密码重置，请放心</h5>"+
    "<div id='question' class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>选择问题</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <select id='SETquestions' lay-verify='required'>\n" +
    "            <option value=''>请选择一个问题</option>\n" +
    "            <option value='你QQ号多少'>你QQ号多少</option>\n" +
    "            <option value='你的小学名字是什么'>你的小学名字是什么</option>\n" +
    "            <option value='你的初中名字叫什么'>你的初中名字叫什么</option>\n" +
    "            <option value='你的配偶叫什么'>你的配偶叫什么</option>\n" +
    "            <option value='你的第一部手机是什么牌子的'>你的第一部手机是什么牌子的</option>\n" +
    "        </select>\n" +
    "    </div>\n" +
    "</div>\n" +
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>你的答案</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='SETans' type='text' name='title' autocomplete='off' placeholder='请输入答案' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>";

var resUrlHtml="<p>需要您验证密码</p>" +
    "<div class='layui-form-item'>\n" +
    "    <label class='layui-form-label'>密码</label>\n" +
    "    <div class='layui-input-block'>\n" +
    "        <input id='resPwd' type='password' name='title' autocomplete='off' placeholder='请输入密码' class='layui-input'>\n" +
    "    </div>\n" +
    "</div>\n";