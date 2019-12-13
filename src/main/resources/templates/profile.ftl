<#import "parts/common.ftl" as c>

    <@c.page>
        <h5>${username}</h5> ${message?ifExists}
        <form method="post">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Old Password:</label>
                <div class="col-sm-6">
                    <input type="password" name="oldPassword" class="form-control" ${(oldPasswordError??)?string( 'is-invalid', '')} placeholder="Enter old password" />
                    <#if oldPasswordError??>
                            ${oldPasswordError}
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">New password:</label>
                <div class="col-sm-6">
                    <input type="password" name="newPassword" class="form-control" placeholder="Enter new password" />
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${email!''}" />
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button class="btn btn-primary" type="submit">Save</button>
        </form>
    </@c.page>