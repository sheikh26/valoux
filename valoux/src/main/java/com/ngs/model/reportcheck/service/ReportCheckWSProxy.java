package com.ngs.model.reportcheck.service;

public class ReportCheckWSProxy implements com.ngs.model.reportcheck.service.ReportCheckWS_PortType {
  private String _endpoint = null;
  private com.ngs.model.reportcheck.service.ReportCheckWS_PortType reportCheckWS_PortType = null;
  
  public ReportCheckWSProxy() {
    _initReportCheckWSProxy();
  }
  
  public ReportCheckWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initReportCheckWSProxy();
  }
  
  private void _initReportCheckWSProxy() {
    try {
      reportCheckWS_PortType = (new com.ngs.model.reportcheck.service.ReportCheckWS_ServiceLocator()).getReportCheckWSImplPort();
      if (reportCheckWS_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)reportCheckWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)reportCheckWS_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (reportCheckWS_PortType != null)
      ((javax.xml.rpc.Stub)reportCheckWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ngs.model.reportcheck.service.ReportCheckWS_PortType getReportCheckWS_PortType() {
    if (reportCheckWS_PortType == null)
      _initReportCheckWSProxy();
    return reportCheckWS_PortType;
  }
  
  public java.lang.String processRequest(java.lang.String arg0) throws java.rmi.RemoteException{
    if (reportCheckWS_PortType == null)
      _initReportCheckWSProxy();
    return reportCheckWS_PortType.processRequest(arg0);
  }
  
  public java.lang.String getReportCheckDateResult(java.lang.String arg0) throws java.rmi.RemoteException{
    if (reportCheckWS_PortType == null)
      _initReportCheckWSProxy();
    return reportCheckWS_PortType.getReportCheckDateResult(arg0);
  }
  
  public byte[] downloadPDFReport(java.lang.String arg0) throws java.rmi.RemoteException{
    if (reportCheckWS_PortType == null)
      _initReportCheckWSProxy();
    return reportCheckWS_PortType.downloadPDFReport(arg0);
  }
  
  public java.lang.String processRequest1(java.lang.String arg0) throws java.rmi.RemoteException{
    if (reportCheckWS_PortType == null)
      _initReportCheckWSProxy();
    return reportCheckWS_PortType.processRequest1(arg0);
  }
  
  public java.lang.String getColoredStonePearlResults(java.lang.String arg0) throws java.rmi.RemoteException{
    if (reportCheckWS_PortType == null)
      _initReportCheckWSProxy();
    return reportCheckWS_PortType.getColoredStonePearlResults(arg0);
  }
  
  
}