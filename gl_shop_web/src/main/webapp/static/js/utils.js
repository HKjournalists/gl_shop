function calculatePrice(price, num) {
  var p = price, n = num;
  if (typeof a === 'string') {
    p = parseFloat(price);
  }

  if (typeof num === 'string') {
    n = parseFloat(num);
  }

  var total = Math.round(p * n * 100);
  return total / 100;
}
