[![Apache Sling](https://sling.apache.org/res/logos/sling.png)](https://sling.apache.org)

&#32;[![Build Status](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-repoinit-parser/job/master/badge/icon)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-repoinit-parser/job/master/)&#32;[![Test Status](https://img.shields.io/jenkins/tests.svg?jobUrl=https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-repoinit-parser/job/master/)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-repoinit-parser/job/master/test/?width=800&height=600)&#32;[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-repoinit-parser&metric=coverage)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-repoinit-parser)&#32;[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-repoinit-parser&metric=alert_status)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-repoinit-parser)&#32;[![JavaDoc](https://www.javadoc.io/badge/org.apache.sling/org.apache.sling.repoinit.parser.svg)](https://www.javadoc.io/doc/org.apache.sling/org-apache-sling-repoinit-parser)&#32;[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.sling/org.apache.sling.repoinit.parser/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.apache.sling%22%20a%3A%22org.apache.sling.repoinit.parser%22)&#32;[![repoinit](https://sling.apache.org/badges/group-repoinit.svg)](https://github.com/apache/sling-aggregator/blob/master/docs/group/repoinit.md) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

# Apache Sling RepoInit Parser

This module is part of the [Apache Sling](https://sling.apache.org) project.

It implements the [Repository Initialization language](https://sling.apache.org/documentation/bundles/repository-initialization.html) parser.

To parse repoinit statements use the [RepoInitParser](./src/main/java/org/apache/sling/repoinit/parser/RepoInitParser.java) service.

The companion [jcr-repoinit](https://github.com/apache/sling-org-apache-sling-jcr-repoinit) module can execute the resulting `Operations` on a
JCR content repository.

The JavaCC parser grammar is defined in the [RepoInitGrammar.jjt](./src/main/javacc/RepoInitGrammar.jjt) file, which has links to
the JavaCC documentation.

## Dependent modules

The [Feature Model Analyser](https://github.com/apache/sling-org-apache-sling-feature-analyser) uses this
parser to validate repoinit statements embedded in feature models. It should be kept up to date with new
releases of this module.

The [bnd.bnd](./bnd.bnd) file defines an `org.apache.sling.repoinit.language` OSGi capability which indicates
the version of the repoinit language that this parser implements. That value should be incremented when the
grammar changes, so that modules that depend on those changes can require the appropriate version.

## Documenting the language

The [Sling Website Repository Initialization page](https://sling.apache.org/documentation/bundles/repository-initialization.html) describes
the general repoinit principles and is meant to show examples of _all_ possible language constructs.

To this end, we maintain a [test scenario that includes all those constructs](./src/test/resources/testcases/test-99.txt) as part of this module's unit tests, and
slightly adapt that test's repoinit script for the documentation page.

If adding new statements of options, please keep that `test-99` in sync so that it reflects the full language syntax, and update the
documentation page accordingly.
