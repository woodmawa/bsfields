<%@ page import="com.softwood.domain.Holder" %>
<g:if test="${pageScope.variables.persistentProperty && pageScope.variables.persistentProperty instanceof org.grails.datastore.mapping.model.types.ManyToOne}" >
            <div class="input-group text col-sm-8"  >
                <%--<input name="${property}" type='select' class="form-control" value ="${pageScope.variables.value}" placeholder="<empty>" />--%>

                <g:select name="${property}" optionKey="id" from="${com.softwood.domain.Holder.list()}" value="${persistentProperty.getOwner().getIdentity()}" class="form-control" />

                <div class="input-group-append" >
                    <g:set var="propLink" value="${bsf.getPropertyValuesController()}" />
                    <button class="btn btn-icon-fixed-width btn-outline-secondary "
                            disabled aria-disabled="true"
                            type="button"  >
                    <i class="fas fa-link" />
                    </button>
                </div>
            </div>
</g:if>
<g:elseif test="${pageScope.variables.persistentProperty && pageScope.variables.persistentProperty instanceof org.grails.datastore.mapping.model.types.OneToMany}" >
            <div class="input-group text col-sm-8"  >
                <input name="${property}" type='text' class="form-control" value ="${pageScope.variables.value}" placeholder="<empty>" />
                <div class="input-group-append dropdown dropright" >
                    <button class="btn btn-icon-fixed-width btn-outline-secondary  btn-link" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="badge badge-primary badge-pill" style="background-color: DarkSeaGreen  ; ">${pageScope.variables.value?.size() ?: 0 }</span>
                        <i class="fas fa-link"></i>
                    </button>

                    <%--<a href="#" data-toggle="popover" title="Popover Header" data-content="Some content inside the popover">Toggle popover</a>--%>
                    <div class="dropdown-menu" aria-labelledby="references" >
                        <table class='table table-bordered table-striped table-condensed ' >

                            <thead >
                            <tr>
                                <th class="bg-info text-white" scope="col">References</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- for each property createLink and use as href for anchor --%>
                            <g:each in="${pageScope.variables.value}" var="p">
                                <tr>
                                    <td scope="row"  >
                                        <a href="${bsf.getPropertyValuesController(property:p)}" >${p?.toString()}</a>
                                    </td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
</g:elseif>
<g:else>
            <%--  label output by _list <label class='control-label' > ${label} </label> --%>
            <div class="input-group text col-sm-8"  >
                <input name="${property}" type='text'  class="form-control" value="${value?.toString()}" placeholder="<empty>"/>
                <div class="input-group-append" >
                    <button class="btn btn-icon-fixed-width btn-outline-secondary " type="button" >
                        <i class="fal fa-map-marker-question"></i>

                    </button>
                </div>
</g:else>