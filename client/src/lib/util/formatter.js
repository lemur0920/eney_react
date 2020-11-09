
export const phoneFormat = (num, type) => {
    let formatNum = '';
    try{
        if (num.length == 11) {
            if (type == 0) {
                formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
            } else {
                formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
            }
        } else if (num.length == 8) {
            formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
        } else {
            if (num.indexOf('02') == 0) {
                if (type == 0) {
                    formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
                } else {
                    formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
                }
            } else {
                if (type == 0) {
                    formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
                } else {
                    formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
                }
            }
        }
    } catch(e) {
        formatNum = num;
        console.log(e);
    }

    return formatNum;

}

export const  numberWithCommas = (x) =>{
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function camelToSnakeCase(str) {
    return str
        .split(/(?=[A-Z])/)
        .map(x => x.toLowerCase())
        .join('_');
}
export const snakeToCamel = (str) => str.replace(
    /([-_][a-z])/g,
    (group) => group.toUpperCase()
        .replace('-', '')
        .replace('_', '')
);

export const isNumber = (str) => {
    const regexp = /^[0-9]*$/

    if( !regexp.test(str) ) {
        return false
    }else{
        return true
    }
}

export const formatDate = (date) => {
    return date.getFullYear() + '-' +
        (date.getMonth() + 1) + '-' +
        date.getDate() + ' ' +
        date.getHours() + ':' +
        date.getMinutes();
}


export const formatDateYYMMDD = (date) => {
    return date.getFullYear() + '-' +
        (date.getMonth() + 1) + '-' +
        date.getDate();
}