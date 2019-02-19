<%-- _widget template is used to create an editable input field
variable

you must add a name attribute if you want the value to be posted on a submit action in the form

--%>


<div class="container fieldcontain col-sm-12" >
    <div class="form-group form-inline">
        <%--  label output by _list <label class='control-label' > ${label} </label> --%>
        <div class="input-group col-sm-8 text"  >
            <input name="${property}" type='text' id="${property}-label" class="form-control" value="${value?.toString()}" placeholder="<empty>"/>
            <div class="input-group-append" >
                <button class="btn btn-icon-fixed-width btn-outline-secondary " disabled aria-disabled="true" type="button" >
                    <i class="far fa-edit"></i>
                </button>
            </div>
        </div>
    </div>

</div>