/**
 * ReportCheckWS_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ngs.model.reportcheck.service;

public interface ReportCheckWS_PortType extends java.rmi.Remote {
	public java.lang.String processRequest(java.lang.String arg0) throws java.rmi.RemoteException;

	public java.lang.String getReportCheckDateResult(java.lang.String arg0) throws java.rmi.RemoteException;

	public byte[] downloadPDFReport(java.lang.String arg0) throws java.rmi.RemoteException;

	public java.lang.String processRequest1(java.lang.String arg0) throws java.rmi.RemoteException;

	public java.lang.String getColoredStonePearlResults(java.lang.String arg0) throws java.rmi.RemoteException;
}
