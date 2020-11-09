var host = location.host.toLowerCase();
var currentAddress = location.href;
if (host.indexOf("www")== -1)
{
    currentAddress = currentAddress.replace("//","//www.");
    location.href = currentAddress;
}
