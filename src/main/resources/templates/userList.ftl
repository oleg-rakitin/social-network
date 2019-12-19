<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
List of users

<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Role</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
                 <#if isAdmin>
                    <td><a href="/user/${user.id}">edit</a></td>
                 </#if>
        </tr>
    </#list>
    </tbody>
</table>
    <form action="/user/save" method="get">
         <button type="submit">Save</button>
      </form>
</@c.page>
