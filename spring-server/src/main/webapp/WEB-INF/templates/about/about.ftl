[#import "/spring.ftl" as spring]

    <div class="box box-primary">
        <div class="box-body row">
        	<div class="col-md-12">
         	<form role="form" class="form-horizontal">
         		<fieldset>
         			<legend>[@spring.messageText "about.page.section.version.title", "Version" /]</legend>
					[@spring.messageText 'about.currentVersion' "Version actuelle"/]&nbsp;:&nbsp;[@spring.message "about.versionNumber" /]
         		</fieldset>
         		<br>
         		<fieldset>
         			<legend>[@spring.message "about.page.section.infos.title" /]</legend>
					<strong>[@spring.message 'about.editedBy'/]&nbsp;<a href="http://www.berger-levrault.com" target="_blank" class="notranslate noAjax">Berger-Levrault</a></strong><br/>
					<strong>[@spring.message 'about.openSourceInformation'/].&nbsp;<a href="[@spring.url '/about/licences' /]">[@spring.message 'about.moreInformation'/]</a></strong>
         		</fieldset>
			</form>
			</div>
        </div>
    </div>
