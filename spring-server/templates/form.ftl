[#import "/spring.ftl" as spring]
[#import "/menu_macro.ftl" as menu]
[#import "/form_macro.ftl" as form]
[#import "/static_macro.ftl" as static]
[@layout.extends name="layouts/base.ftl"]

	[#-- Title --]
   	[@layout.put block="title" type="replace"]
       <title>Add: ${domain}</title>
    [/@layout.put]

	[#-- Head --]
    [@layout.put block="head" type="append"]
        [#if errorTag??]<error/>[/#if]
	[/@layout.put]
	

	[#-- Menu --]
    [@layout.put block="menu" type="replace"]
		[#-- [@menu.menu "SECTION" /] --]
	[/@layout.put]


    [#-- Content --]
    [@layout.put block="contents"]

        [#-- [@menu.submenu "SECTION" "SUBSECTION" "Add ${domain}" /] --]

        <div class="box box-primary">
            <div id="box-content" class="box box-body">
            	<!-- tab-refresh="/${domain?uncap_first}/list" -->
                <form name="${domain?uncap_first}" class="form-horizontal" action="[@spring.url '/${domain?uncap_first}/add.do' /]" method="post">
                [@form.csrf /]
                [@spring.bind "${domain?uncap_first}.*" /]

                <fieldset>
                    <legend>[@spring.messageText "${app}.${domain?uncap_first}add.form.title" "Ajouter" /]</legend>

                    [#-- Liste des erreurs --]
                    <div class="row">
                        <div class="col-md-10 col-md-offset-2">
                            [@form.fieldErrorMessages prefix="${app}.${domain?uncap_first}add.form.validator" command="${domain?uncap_first}" paths=['${fields?keys?join("', '")}'] attributes="class=\"error\"" additionalErrors=errorList /]
                        </div>
                    </div>

                    [#-- id --]
                    [#if ${domain?uncap_first}.id??]
						[@spring.formHiddenInput "${domain?uncap_first}.id" /]				                    	
                    [/#if]

					<#list fields?keys as key>
						<#if key != "id">
							<#if fields[key]?ends_with("Date")>
			                    [#-- ${key} --]
			                    <div class="form-group [@form.onError "${domain?uncap_first}.${key}" /]">
			                        <label for="${key}" class="col-sm-2 control-label">[@spring.messageText "${app}.${domain?uncap_first}add.form.field.${key}" "${key}" /]${requiredfields[key]?string("<span class='required'>*</span>", "")}</label>
			                        <div class="col-sm-2">[@form.formInput "${domain?uncap_first}.${key}" "text" "aria-required='true' class='form-control' data-date-format='dd/mm/yyyy' placeholder='dd/mm/yyyy'" /]</div>
			                    </div>
							<#elseif fields[key]?ends_with("Boolean")>
			                    [#-- ${key} --]
			                    <div class="form-group">
			                        <label for="${key}" class="col-sm-2 control-label">[@spring.messageText "${app}.${domain?uncap_first}add.form.field.${key}" "${key}" /]${requiredfields[key]?string("<span class='required'>*</span>", "")}</label>
			                        <div class="col-sm-10">
			                            <label class="toggle">
			                                [@form.formCheckbox "${domain?uncap_first}.${key}" /]
			                                <span class="handle"></span>
			                            </label>
			                        </div>
			                    </div>
							<#else>
			                    [#-- ${key} --]
			                    <div class="form-group [@form.onError "${domain?uncap_first}.${key}" /]">
			                        <label for="${key}" class="col-sm-2 control-label">[@spring.messageText "${app}.${domain?uncap_first}add.form.field.${key}" "${key}" /]${requiredfields[key]?string("<span class='required'>*</span>", "")}</label>
			                        <div class="col-sm-10">[@form.formInput "${domain?uncap_first}.${key}" "text" " aria-required='true' class='form-control'" /]</div>
			                    </div>
							</#if>
						</#if>
					</#list>

					<#list foreignFields?keys as key>
							<div class="form-group">
		                        <label for="${key?uncap_first}Id" class="col-sm-2 control-label">${key?cap_first}</label>
		                        <div class="col-sm-10">
									<select class="form-control" name="${key?uncap_first}Id" id="${key?uncap_first}Id">
									  	[#list ${key?uncap_first}List?keys as key]
									   	<option value="${r"${"}key}" [#if key == ${key?uncap_first}Id]selected[/#if]>${r"${"}${key?uncap_first}List[key]}</option>
									   	[/#list]
									</select>
		                        </div>
							</div>
					</#list>

                    [#-- select 
                    <div class="form-group">
                        <label for="country" class="col-sm-2 control-label">[@spring.messageText "user.useradd.form.field.country" "Country" /]</label>
                        <div class="col-sm-10">[@form.formSingleSelect "userCommand.country" countryList "class='form-control'" /]</div>
                    </div>
                    --]

                    [#-- Multi select 
                    <div class="form-group">
                        <label for="javaSkills" class="col-sm-2 control-label">[@spring.messageText "user.useradd.form.field.javaSkills" "JavaSkills" /]</label>
                        <div class="col-sm-10">[@form.formMultiSelect "userCommand.javaSkills" javaSkillsList "class='form-control'" /]</div>
                    </div>
                    --]

                    [#-- Action --]
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" class="btn btn-primary" value="[@spring.messageText "${app}.${domain?uncap_first}add.form.submit" "Enregistrer" /]"/>
                        </div>
                    </div>
                    </fieldset>
                </form>
                
	                
	           <script type="text/javascript">
	                    $.fn.datepicker.defaults.format = "dd/mm/yyyy";
	                    
	                	<#list fields?keys as key>
							<#if fields[key]?ends_with("Date")>
								$("#${key}").datepicker({
			                        dateFormat: "dd/mm/yyyy",
			                        autoclose: true,
			                        todayBtn: true,
			                        todayHighlight: true
			                    });	
							</#if>
						</#list>
						
	
	                    $('input').iCheck({
	                        checkboxClass: 'icheckbox_minimal-grey',
	                        radioClass: 'iradio_minimal-grey'
	                    });
	
	                    /*
	                    setTimeout(function(){
	                        $("#javaSkills").chosen({max_selected_options: 5});
	                    }, 50);
	                    */
	           </script>

	
	            <style>
	                .error {
	                    color:darkred;
	                }
	            </style>
                
            </div>
        </div>


            <div id="result"></div>

    [/@layout.put]

    [@layout.put block="footer" type="replace"]
	[/@layout.put]
    
[/@layout.extends]