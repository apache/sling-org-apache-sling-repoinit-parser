[<img src="https://sling.apache.org/res/logos/sling.png"/>](https://sling.apache.org)

 [![Build Status](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-repoinit-parser/job/master/badge/icon)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-repoinit-parser/job/master/) [![Test Status](https://img.shields.io/jenkins/tests.svg?jobUrl=https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-repoinit-parser/job/master/)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-repoinit-parser/job/master/test/?width=800&height=600) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-repoinit-parser&metric=coverage)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-repoinit-parser) [![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-repoinit-parser&metric=alert_status)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-repoinit-parser) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.sling/org.apache.sling.repoinit.parser/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.apache.sling%22%20a%3A%22org.apache.sling.repoinit.parser%22) [![JavaDocs](https://www.javadoc.io/badge/org.apache.sling/org.apache.sling.repoinit.parser.svg)](https://www.javadoc.io/doc/org.apache.sling/org.apache.sling.repoinit.parser) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![repoinit](https://sling.apache.org/badges/group-repoinit.svg)](https://github.com/apache/sling-aggregator/blob/master/docs/groups/repoinit.md)

# Apache Sling RepoInit Parser

This module is part of the [Apache Sling](https://sling.apache.org) project.

Parser for the [Repository Initialization language](https://sling.apache.org/documentation/bundles/repository-initialization.html) used in Sling.

To parse repoinit statements use the [RepoInitParser](./src/main/java/org/apache/sling/repoinit/parser/RepoInitParser.java) service.

See the [Sling Website Repository Initialization page](https://sling.apache.org/documentation/bundles/repository-initialization.html) for more information.

## Module dependencies

The [Feature Model Analyser](https://github.com/apache/sling-org-apache-sling-feature-analyser) uses this
module to validate repoinit statements embedded in feature models. It should be kept up to date with new
releases of this parser.
