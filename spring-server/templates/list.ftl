[#import "/spring.ftl" as spring]
[#import "/pagination_macro.ftl" as pagination]
[#import "/menu_macro.ftl" as menu]
[#import "/form_macro.ftl" as form]
[#import "/static_macro.ftl" as static]
[#import "/utils_macro.ftl" as utils]
[#import "/macro_popup.ftl" as modal]

[@layout.extends name="layouts/base.ftl"]
${r""}
	[#-- Title --]
   	[@layout.put block="title" type="replace"]
       <title>Liste: ${domain}</title>
    [/@layout.put]

	[#-- Menu --]
    [@layout.put block="menu" type="replace"]
		[#--[@menu.menu "SECTION" /]--]
	[/@layout.put]

    [#-- Content --]
    [@layout.put block="contents"]
		
        [#--[@menu.submenu "SECTION" "SUBSECTION" "TITLE" /]--]

            [#if successNotify??]
                <div id="success" class="alert alert-success" role="alert">
                    <span class="glyphicon glyphicon-ok"></span>&nbsp;</span>[@spring.messageText "${app}.${domain?uncap_first}List.successmessage", "Modification effectuée" /]
                </div>
            [/#if]

            <div class="box box-primary">
                <div class="box-body">

                    <div class="box-header">
                        <h2 class="box-title">[@spring.messageText "${app}.${domain?uncap_first}List.table.title", "Liste des ${domain}s" /]</h2>
                    </div>

                    <table class="table table-striped no-userselection listTable">
                        <caption class="sr-only">[@spring.messageText "${app}.${domain?uncap_first}List.table.caption", "Liste des ${domain?uncap_first}s" /]</caption>
                        <thead>
                        <tr>
							<#list fields?keys as key>  
                            <th id="header_${key}" scope="col">
                                [@pagination.sort '/${domain?uncap_first}/list.do' "${key}" "${r"${"}springMacroRequestContext.getMessage('${app}.${domain?uncap_first}List.table.${key}.title', '${key}')}" /]</th>
                            </#list>

							<#list foreignFields?keys as key>  
                            <th id="header_${key?uncap_first}" scope="col">
                            	[@spring.messageText "${app}.${domain?uncap_first}List.table.${key?uncap_first}.title", "${key?uncap_first}" /]
                            </th>
                            </#list>
                            
                            <th id="header_actions" scope="col">
                            	[@spring.messageText "${app}.${domain?uncap_first}List.table.action.title", "Actions" /]
                            </th>
                                
                        </tr>
                        </thead>
                        <tbody>
                            [#list ${domain?uncap_first}s as ${domain?uncap_first}]
                            <tr>
								
								<#list fields?keys as key>  
                                <td headers="header_${key}">
                                	<#if fields[key]?ends_with("Boolean")>
                                		<span style="color:${r"${"}${domain?uncap_first}.${key}?string("green", "red")};" class="glyphicons glyphicons-${r"${"}${domain?uncap_first}.${key}?string("circle-ok", "circle-remove")}"></span>
                                	<#else>
                                    	${r"${"}${domain?uncap_first}.${key}}
                                    </#if>
                                </td>
                                </#list>
								<#list foreignFields?keys as key>  
                                <td headers="header_${key?uncap_first}">
                                	[#if ${domain?uncap_first}.${key?uncap_first}??]
                                    	${r"${"}${domain?uncap_first}.${key?uncap_first}.name}
                                    [/#if]
                                </td>
                                </#list>

                                
                                <td headers="header_actions">
                                    <a href="[@spring.url '/${domain?uncap_first}/modify.do' /]?id=${r"${"}${domain?uncap_first}.id}" class="btn btn-info"><span class="glyphicons glyphicons-edit"></span><span class="sr-only">Update</span></a>
									<a href="[@spring.url '/${domain?uncap_first}/delete.do' /]?id=${r"${"}${domain?uncap_first}.id}" class="btn btn-danger"><span class="glyphicons glyphicons-remove"></span><span class="sr-only">Delete</span></a>
                                </td>
                            </tr>
                            [/#list]
                        </tbody>
                    </table>
                     <nav id="pagination" class="pull-right">
                         <ul class="pagination">
                            <li>[@pagination.first '/${domain?uncap_first}/list.do' /]</li>
                            <li>[@pagination.previous '/${domain?uncap_first}/list.do' /]</li>
                            <li>[@pagination.numbers relativeUrl='/${domain?uncap_first}/list.do' separator= "</li><li>" /]</li>
                            <li>[@pagination.next '/${domain?uncap_first}/list.do' /]</li>
                            <li>[@pagination.last '/${domain?uncap_first}/list.do' /]</li>
                         </ul>
                    </nav>
                    [@pagination.counter /]

                    [#-- Boutons d'actions --]
                    <div class="form-group">
                        <a href="[@spring.url '/${domain?uncap_first}/initadd.do' /]" class="btn btn-primary">[@spring.messageText "${app}.${domain?uncap_first}List.action.new" "Ajouter" /]</a>
                    </div>


                </div>                
            </div>


		<!-- Script Jquery pour la notification et les checkbox-->
		<script>
			if ($("#success").length) {
				setTimeout(function(){$("#success").remove();}, 3000);
			}
            $('input').iCheck({
                checkboxClass: 'icheckbox_minimal-grey',
                radioClass: 'iradio_minimal-grey'
            });

            $(".listTable").click(function(event) {
                if (event.target.type !== 'checkbox') {
                    $(':checkbox', this).trigger('click');
                    $(':checkbox', this).iCheck('toggle');
                }
            });

		</script>

    [/@layout.put]

[/@layout.extends]