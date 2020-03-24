<#import "parts/common.ftl" as c>

<@c.page>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/main" class="form-inline">
            <input type="text" pattern="[a-zA-Z0-9а-яА-Я]{1,30}"  name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search by tag"  />
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>
</div>

<#include "parts/messageEdit.ftl" />

<#include "parts/messageList.ftl" />

</@c.page>
