<#import "parts/common.ftl" as c>
<#import "parts/loginTemp.ftl" as l>

<@c.page>
    <div class="mb-3">Добавить пользователя</div>
    ${message?ifExists}
    <@l.login "/registration" true/>
</@c.page>