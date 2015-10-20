$.validator.addMethod("mobileCN", function(value, element) {
	return this.optional(element) || /^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(value);
}, "Please specify a valid mobile number");

$.validator.addMethod("bankCardNumber", function(value, element) {
    if ($.payment) {
	    return this.optional(element) || $.payment.validateCardNumber(value);
	}
    console.log("bankCardNumber method requires stripe/jquery.payment.");
    return false;
}, "Please specify a valid bank card number");

$.validator.addMethod("idCardCN", function(value, element) {
    if (value && (value.length == 15 || value.length == 18)) {
        var testResult = false;
        if (value.length == 15) {
            testResult = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
        } else {
            testResult = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
        }
        return this.optional(element) || testResult;
    } else {
        return false;
    }
}, "Please specify a valid id card number");



