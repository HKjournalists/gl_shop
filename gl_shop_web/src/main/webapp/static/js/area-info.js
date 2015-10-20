var _PROVINCES = [];
var _CITIES = {};
var _AREA_DICT = {};

function populateArea(htmlId, list, selectId) {
    $('#' + htmlId + ' option[value!=""]').remove();
    $.each(list, function(index, elem) {
        $('#' + htmlId).append('<option value="' + elem.val + '">' + elem.name + '</option>');
    });

    $('#' + htmlId).val(selectId ? selectId : '');
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
    $('#' + cityDomId + ' option[value!=""]').remove();
    $('#' + districtDomId + ' option[value!=""]').remove();
    var cities = _CITIES[selectedProvince];
    if(cities != null && cities.length > 0){
    	$.each(cities, function(index, elem) {
            $('#' + cityDomId).append('<option value="' + elem.val +'">' + elem.name + '</option>');
        });
    }
}

function cityChange(selectCity, districtDomId) {
    $('#' + districtDomId + ' option[value!=""]').remove();
    var cities = _CITIES[selectCity];
    if(cities != null && cities.length > 0){
    	$.each(cities, function(index, elem) {
            $('#' + districtDomId).append('<option value="' + elem.val +'">' + elem.name + '</option>');
        });
    }
}

function populateCompanyAddresses() {
    var tradingPlaceMethod = $('#addresstype').val();
    var addressList = $('#addressid');
    if (tradingPlaceMethod === '1') {
        if (companyAddresses && companyAddresses.length > 0) {
            addressList.empty().append('<option value="">请选择</option>');
            $.each(companyAddresses, function(index, elem) {
                addressList.append('<option value="' + elem.id + '">' + elem.address + '</option>');
            });
        }
    }
}

function populateArea(htmlId, list, selectId) {
    $('#' + htmlId + ' option[value!=""]').remove();
    $.each(list, function(index, elem) {
        $('#' + htmlId).append('<option value="' + elem.val + '">' + elem.name + '</option>');
    });

    $('#' + htmlId).val(selectId ? selectId : '');
}