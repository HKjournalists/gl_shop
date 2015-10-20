PRODUCT_PROPERTY_MAPPING = {
    'MUD_CONTENT': 'mudContent',
    'CLAY_CONTENT': 'clayContent',
    'APPARENT_DENSITY': 'apparentDensity',
    'CONSISTENCY_INDEX': 'consistencyIndex',
    'MOISTURE_CONTENT': 'moistureContent',
    'BULK_DENSITY': 'bulkDensity',
    'CRUSHING_VALUE_INDEX': 'crushingValueIndex',
    'ELONGATED_PARTICLES': 'elongatedParticles',
    'paddress': 'paddress'
}

PRODUCT_ADDITIONAL_REQUIRED_FIELDS = {
    'G001': ['crushingValueIndex', 'elongatedParticles'],
    'G002': ['moistureContent']
}

var productG002Types = null;
var productG001Specs = null;

function composeProductName(elem) {
    var name = elem.name;
    if (elem.minv > 0 && elem.maxv === 0) {
        name += '(>=' + elem.minv.toFixed(1) + ')';
    } else if (elem.maxv > 0) {
        name += '(' + elem.minv.toFixed(1) + '-' + elem.maxv.toFixed(1) + ')';
    }
    return name;
}

function loadProductTypes(contextPath, productCode, callback) {
    var productTypeURL = '/product/code/{pcode}/';
    loadJsonData(contextPath, productTypeURL, {pcode: productCode}, callback);
}

function loadProductSpecs(contextPath, productType, callback) {
    var productSpecURL = '/product/spec/{ptype}/';
    loadJsonData(contextPath, productSpecURL, {ptype: productType}, callback);
}

function loadProductProperties(contextPath, pid, callback) {
    var productPropertyURL = '/product/props/{pid}/';
    loadJsonData(contextPath, productPropertyURL, {pid: pid}, callback);
}

function loadOrderProductProperties(contextPath, ppid, callback) {
    var productPropertyURL = '/product/props/order/{ppid}/';
    loadJsonData(contextPath, productPropertyURL, {ppid: ppid}, callback);
}

function initFormProductData(callbackForG001, callbackForG002) {
    loadProductTypes(contextPath, 'G001', function(data) {
        productG001Specs = data;
        if (callbackForG001) { callbackForG001(data) };
    });

    loadProductTypes(contextPath, 'G002', function(data) {
        productG002Types = data;
        if (callbackForG002) { callbackForG002(data) };
    });
}

function initContractProductFields() {
    for (var id in PRODUCT_ADDITIONAL_REQUIRED_FIELDS) {
        var fields = PRODUCT_ADDITIONAL_REQUIRED_FIELDS[id];
        for (var i = 0; i < fields.length; i++) {
            $('#' + fields[i] + 'Div').hide();
        }
    }
}

function toggleProductFieldsVisibility(product) {
    for (var id in PRODUCT_ADDITIONAL_REQUIRED_FIELDS) {
        var visibility = (id === product)
        var fields = PRODUCT_ADDITIONAL_REQUIRED_FIELDS[id];
        for (var i = 0; i < fields.length; i++) {
            if (visibility) {
                $('#' + fields[i] + 'Div').show();
                $('#' + fields[i]).prop('disabled', false);
            } else {
                $('#' + fields[i] + 'Div').hide();
                $('#' + fields[i]).prop('disabled', true);
            }
        }
    }
}

function populateProductSpec(productSpecDomId, data, nameField, selectSpecId) {
    var productSpecList = $('#' + productSpecDomId);
    productSpecList.empty().append('<option value="">请选择</option>');
    if (data) {
        $.each(data, function(index, elem) {
            elem['name'] = elem[nameField];
            var opt = '<option value=\'' + JSON.stringify(elem) + '\'';
            if (selectSpecId && selectSpecId == elem.psize) {
              opt += ' selected';
            }
            opt += '>' + composeProductName(elem) + '</option>';
            productSpecList.append(opt);
        });
        productSpecList.prop('disabled', false);
    }
}

function populateProductProperties(val, orderProductId) {
    var values = JSON.parse(val);
    $('input[name=pid]').val(values.id);
    var ppid = values.id;
    if (values.unit) {
        $('select[name="unit"]').val(values.unit.val);
    }
    var callback = function(data) {
        $.each(data, function(index, elem) {
            var field = PRODUCT_PROPERTY_MAPPING[elem.code];
            if (orderProductId) {
                $('input[name=' + field + 'Id]').val(elem.pproid);
                $('input[name=' + field + ']').val(elem.content);
            } else {
                $('input[name=' + field + 'Id]').val(elem.id);
            }
        });
        if (orderProductId) {
          $('input[name=paddress]').val(values.paddress);
          $('input[name=pcolor]').val(values.pcolor);
        }
    };
    if (orderProductId) {
        ppid = orderProductId;
        loadOrderProductProperties(contextPath, ppid, callback);
    } else {
        loadProductProperties(contextPath, ppid, callback);
    }
}

function bindSubProductFormUI(prodTypeId, prodSpecId, orderProductId) {
  $('#' + prodTypeId).change(function() {
    var typeId = $(this).val();
    if (typeId !== '') {
      loadProductSpecs(contextPath, typeId, function(data) {
        populateProductSpec(prodSpecId, data, 'pname');
        $('#' + prodSpecId).prop('disabled', false);
      });
    }
  });
  $('#' + prodSpecId).change(function() {
    populateProductProperties($(this).val(), orderProductId);
  });
}

function bindTopProductFormUI(jqObject, prodTypeId, prodSpecId) {
    var code = $(jqObject).val();
    if (code === 'G001') {
      $('#' + prodTypeId).prop('disabled', true);
      $('#' + prodSpecId).prop('disabled', false);
      populateProductSpec(prodSpecId, productG001Specs, 'pname');
    } else if (code === 'G002') {
      $('#' + prodTypeId).prop('disabled', false);
      $('#' + prodSpecId).prop('disabled', false);
      $('#' + prodSpecId).empty().append('<option value="">请选择</option>');
    }
    toggleProductFieldsVisibility(code);
}

function bindProductSelectFormUI(prodCodeId, prodTypeId, prodSpecId, orderProductId) {
  $('#' + prodCodeId).change(function() {
    bindTopProductFormUI(this, prodTypeId, prodSpecId);
  });
  bindSubProductFormUI(prodTypeId, prodSpecId, orderProductId);
}

function bindProductRadioFormUI(prodCodeName, prodTypeId, prodSpecId, orderProductId) {
  $('input:radio[name="' + prodCodeName + '"]').click(function() {
      bindTopProductFormUI(this, prodTypeId, prodSpecId);
  });

  bindSubProductFormUI(prodTypeId, prodSpecId, orderProductId);
}