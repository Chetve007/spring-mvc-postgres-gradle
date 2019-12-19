<#import "parts/common.ftl" as c>
<#import "parts/loginTemp.ftl" as l>

<@c.page>
    <p>Login page</p>
    <@l.login "/login" />
    <a href="/registration">Добавить User'a</a>
</@c.page>