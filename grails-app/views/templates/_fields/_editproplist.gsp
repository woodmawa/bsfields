<%--
this gsp - controls the default output for each property in a domainObject properties list
it gets invoked by bsf:allbsf when all fields need to be rendered for editing

added card - centred content mx-auto and enabled scroll on card body

This template is wrapped by a form provided in the edit.gsp and create.gsp

each property is

--%>
<div class="card mx-auto" style="width: 110rem; ">
    <div class="card-body container-fluid " style="height: 550px; overflow-y: auto;">
        <ol class="form-group property-list ${domainClass.decapitalizedName}" >
            <g:each in="${domainProperties}" var="p">
                <li class="fieldcontain  form-group ">
                    <%-- replace with below <span id="${p.name}-label" class="property-label"><g:message code="${domainClass.decapitalizedName}.${p.name}.label" default="${p.defaultLabel}" /></span>  --%>
                    <%-- fields renderDefaultField writes label and widget markup from template if any  --%>
                    <label class="property-label col-sm-4" for="${p.name}-label" aria-labelledby="${p.name}-label"><g:message code="${domainClass.decapitalizedName}.${p.name}.label" default="${p.defaultLabel}" /> <g:if test="${p.required}"><span class="required-indicator">*</span></g:if> </label>
                    <div class="property-value form-inline " aria-labelledby="${p.name}-label">${body(p)}</div>
                </li>
            </g:each>
        </ol>
    </div>
</div>


