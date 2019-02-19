<%-- _widget template is used to create an editable input field
variable

--%>


<div class="container fieldcontain col-sm-12" >
    <div class="form-group form-inline">
        <%--  label output by _list <label class='control-label' > ${label} </label> --%>
        <div class="input-group col-sm-8 text"  >

        <g:select name="${label}"  from="${com.softwood.domain.BootstrapTest.BootstrapTypes.values()}" class="form-control" />
        <div class="input-group-append" >
             <button class="btn btn-icon-fixed-width btn-outline-secondary "
                    disabled aria-disabled="true"
                    type="button"  >
                <i class="fas fa-list-ol"></i>
            </button>
        </div>
</div>