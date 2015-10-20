function composeURL(contextPath, baseURL, params) {
    if (contextPath.charAt(contextPath.length - 1) === '/' && baseURL.charAt(0) === '/') {
      contextPath = contextPath.substring(0, contextPath.length - 1);
    }
    var infoURL = baseURL;
    if (contextPath && contextPath !== '/') {
        infoURL = contextPath + infoURL;
    }
    $.each(params, function(key, value) {
        infoURL = infoURL.replace('{' + key + '}', value);
        delete params[key];
    });
    var qs = [];
    $.each(params, function(key, value) {
      qs.push(key + '=' + value);
    });
    if (qs.length > 0) {
      if (infoURL.indexOf('?') != -1) {
        if (infoURL.charAt(infoURL.length - 1) != '&') {
          infoURL += '&';
        }
      } else {
        infoURL += '?';
      }
      infoURL += qs.join('&');
    }
    return infoURL;
}

function loadPlainData(contextPath, url, params, callback) {
    var requestURL = composeURL(contextPath, url, params);
    $.get(requestURL, function(data) {
        callback(data);
    });
}

function loadJsonData(contextPath, url, params, callback) {
    var requestURL = composeURL(contextPath, url, params);
    $.getJSON(requestURL, function(data) {
        callback(data);
    });
}

function loadCompany(contextPath, cid, callback) {
    var companyInfoURL = '/company/{cid}/info/load/';
    loadJsonData(contextPath, companyInfoURL, {cid: cid}, callback);
}

function loadCompanyAddresses(contextPath, cid, callback) {
    var companyAddrURL = '/company/{cid}/addresses/load/';
    loadJsonData(contextPath, companyAddrURL, {cid: cid}, callback);
}

function searchCompany(contextPath, params, callback) {
    var searchUrl = '/company/search/mobile/{mobile}/';
    loadJsonData(contextPath, searchUrl, params, function(data) {
        if (!($.isEmptyObject(data))) {
            if (callback) {
                callback(data);
            } else {
                window.location.href = composeURL(contextPath, "/company/{cid}/info/", {cid: data.cid});
            }
            return;
        }

        alert("没有搜索到符合条件的结果！");
    });
}

var _PROVINCES = [];
var _CITIES = {};
var _AREA_DICT = {};

function populateArea(htmlId, list, selectId) {
    $('#' + htmlId + ' option[value!="0"]').remove();
    $.each(list, function(index, elem) {
        $('#' + htmlId).append('<option value="' + elem.val + '">' + elem.name + '</option>');
    });

    $('#' + htmlId).val(selectId ? selectId : '0');
}

function loadArea(contextPath, callback) {
    loadJsonData(contextPath, '/common/reverPortDock/', {}, function(data) {
        $.each(data, function(index, elem) {
            _AREA_DICT[elem.val] = elem;
            if (elem.pcode === '0') {
                _PROVINCES.push(elem);
                if (!_CITIES[elem.val]) {
                    _CITIES[elem.val] = [];
                }
            } else {
                if (!_CITIES[elem.pcode]) {
                    _CITIES[elem.pcode] = [];
                }
                _CITIES[elem.pcode].push(elem);
            }
        });
        if (callback) {
            callback();
        }
    });
}

function provinceChange(selectedProvince, cityDomId, districtDomId) {
    $('#' + cityDomId + ' option[value!="0"]').remove();
    $('#' + districtDomId + ' option[value!="0"]').remove();
    var cities = _CITIES[selectedProvince];
    $.each(cities, function(index, elem) {
        $('#' + cityDomId).append('<option value="' + elem.val +'">' + elem.name + '</option>');
    });
}

function cityChange(selectCity, districtDomId) {
    $('#' + districtDomId + ' option[value!="0"]').remove();
    var cities = _CITIES[selectCity];
    $.each(cities, function(index, elem) {
        $('#' + districtDomId).append('<option value="' + elem.val +'">' + elem.name + '</option>');
    });
}
