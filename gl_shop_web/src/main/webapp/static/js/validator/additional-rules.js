$.validator.addMethod("mobileCN", function(value, element) {
	return this.optional(element) || /^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(value);
}, "Please specify a valid mobile number");

$.validator.addMethod("bankCardNumber", function(value, element) {
    if ($.payment) {
	    return this.optional(element) || $.payment.validateCardNumber(value);
	}

    console.log("bankCardNumber method requires stripe/jquery.payment.");
    return false;
}, "Please specify a valid mobile number");
