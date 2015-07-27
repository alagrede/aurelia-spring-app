<#--
  #%L
  License Maven Plugin
  %%
  Copyright (C) 2012 Codehaus, Tony Chemit
  %%
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as
  published by the Free Software Foundation, either version 3 of the
  License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Lesser Public License for more details.

  You should have received a copy of the GNU General Lesser Public
  License along with this program.  If not, see
  <http://www.gnu.org/licenses/lgpl-3.0.html>.
  #L%
  -->
<#-- To render the third-party file.
 Available context :

 - dependencyMap a collection of Map.Entry with
   key are dependencies (as a MavenProject) (from the maven project)
   values are licenses of each dependency (array of string)

 - licenseMap a collection of Map.Entry with
   key are licenses of each dependency (array of string)
   values are all dependencies using this license
-->

<#assign pageGenerated = "[@spring.message 'license.pageGenerated'/]"/>
<#assign noDependencies = "[@spring.message 'license.noDependencies'/]"/>
<#assign projectName = "[@spring.message 'license.projectName'/]"/>
<#assign projectVersion = "[@spring.message 'license.projectVersion'/]"/>
<#assign projectArtefact = "[@spring.message 'license.projectArtefact'/]"/>
<#assign projectURL = "[@spring.message 'license.projectURL'/]"/>
<#assign projectLicences = "[@spring.message 'license.projectLicences'/]"/>
<#assign unknown = "[@spring.message 'license.unknown'/]"/>

<!-- ${pageGenerated} : ${.now} -->

<#if dependencyMap?size == 0>
	${noDependencies}
<#else>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>${projectName}</th>
				<th>${projectVersion}</th>
				<th>${projectArtefact}</th>
				<th>${projectURL}</th>
				<th>${projectLicences}</th>
			</tr>
		</thead>
		<tbody>
    		<#list dependencyMap as e>
        		<#assign project = e.getKey()/>
        		<#assign licenses = e.getValue()/>
    			
    			<tr>
					<td>
						<#if project.name?index_of('Unnamed') &gt; -1>
							${project.artifactId}
						<#else>
							${project.name}
						</#if>
					</td>
					<td>${project.version}</td>
					<td>${project.groupId}:${project.artifactId}</td>
					<td>
						<#if project.url??>
							<a href="${project.url}" target="_blank" class="noAjax">
								${project.url}
							</a>
						<#else>
							${unknown}
						</#if>
					</td>
					<td>
						<#list licenses as license>
        					${license}<br/>
    					</#list>
					</td>
				</tr>
    		</#list>
		</tbody>
	</table>
</#if>