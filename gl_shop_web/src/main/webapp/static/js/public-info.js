function loadServiceLogs(context, params, showPanel, callback) {
  loadPlainData(context, '/public/service_logs/{objectId}/{type}/?showPanel=' + showPanel, params,
    function(data) {
      callback(data);
    });
}
