<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
      xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="projects">
  <cruisecontrol>
    <xsl:apply-templates/>
  </cruisecontrol>
</xsl:template>

<xsl:template match="project">
    <xsl:variable name="srcdir">
      <xsl:call-template name="srcdir">
        <xsl:with-param
            name="project"
            select="."/>
      </xsl:call-template>
    </xsl:variable>
    <project name="{@name}">
      <bootstrappers>
	<currentbuildstatusbootstrapper 
	    file="logs/{@name}/currentbuildstatus.txt"/>
      </bootstrappers>
      <modificationset>
        <xsl:for-each select="repo-dependency">
	  <filesystem folder="/eng/cruisecontrol/.maven/repository/{.}"/>
	</xsl:for-each>
        <xsl:for-each select="srcdir-dependency">
          <filesystem>
            <xsl:attribute name="folder">
              <xsl:text>/eng/cruisecontrol/src/</xsl:text>
              <xsl:variable name="project" select="."/>
              <xsl:call-template name="srcdir">
                <xsl:with-param
                    name="project"
                    select="/projects/project[@name = $project]"/>
              </xsl:call-template>
            </xsl:attribute>
          </filesystem>
        </xsl:for-each>
	<cvs localworkingcopy="src/{$srcdir}"/>
      </modificationset>
      <schedule>
        <xsl:if test="@interval">
          <xsl:attribute name="interval">
            <xsl:value-of select="@interval"/>
          </xsl:attribute>
        </xsl:if>
	<maven
	    mavenscript="/eng/pkg/maven/bin/maven"
	    projectfile="src/{$srcdir}/project.xml"
	    goal="{goal}"/>
      </schedule>
      <log dir="logs/{@name}">
      </log>
      <publishers>
	<currentbuildstatuspublisher 
	    file="logs/{@name}/currentbuildstatus.txt"/>
	<htmlemail
	    mailhost="localhost"
	    returnaddress="cruisecontrol@domain.name"
	    returnname="Cruise Control"
	    logdir="logs/{@name}"
	    css="/eng/pkg/cruisecontrol/reporting/jsp/css/cruisecontrol.css"
	    xsldir="/eng/pkg/cruisecontrol/reporting/jsp/xsl"
	    reportsuccess="always"
	    spamwhilebroken="yes">
	  <xsl:if test="remote-project">
	    <xsl:attribute name="skipusers">true</xsl:attribute>
	    <always address="developers@domain.name"/>
	  </xsl:if>
          <!-- map 'User' to address when generated by the filesystem plugin -->
          <map alias="User" address="developers@domain.name"/>
	</htmlemail>
      </publishers>
      <dateformat format="dd/MM/yyyy HH:mm:ss"/>
    </project>
</xsl:template>

<xsl:template name="srcdir">
  <xsl:param name="project"/>
<!--  <xsl:value-of select="$project"/> -->
  <xsl:choose>
    <xsl:when test="$project/srcdir">
      <xsl:value-of select="$project/srcdir"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$project/@name"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
