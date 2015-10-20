var _PRODUCT_PRICE_CACHE = {};

function loadProductPrice(contextPath, area, date, callback) {
    var url = "/tasks/editor/marketinfo/load/{area}/{date}/";
    if (area && date) {
        cache = getProductPriceCache(area, date);
        if (cache && callback) {
            callback(cache);
            return;
        }
    }
    loadJsonData(contextPath, url, {area: area, date: date}, function(data) {
        setProductPriceCache(area, date, data);
        if (callback) {
            callback(data);
        }
    })
}

function loadProductPriceForecast(contextPath, area, date, callback) {
    var url = "/tasks/editor/marketinfo/forecast/load/{area}/{date}/";
    if (area && date) {
        cache = getProductPriceCache(area, date);
        if (cache && callback) {
            callback(cache);
            return;
        }
    }
    loadJsonData(contextPath, url, {area: area, date: date}, function(data) {
        var firstDay = moment(date).startOf('week').format('YYYY-MM-DD');
        setProductPriceCache(area, firstDay, data);
        if (callback) {
            callback(data);
        }
    })
}

function expireProductPriceCache(area, date) {
    _PRODUCT_PRICE_CACHE[area + '_' + date] = null;
}

function getProductPriceCache(area, date) {
    return _PRODUCT_PRICE_CACHE[area + '_' + date];
}

function setProductPriceCache(area, date, data) {
    _PRODUCT_PRICE_CACHE[area + '_' + date] = data;
}
