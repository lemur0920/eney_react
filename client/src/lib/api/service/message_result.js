import client from '../client';
import qs from "qs";

export const getTemplateList = ({page}) => {
    const queryString = qs.stringify({
        page
    });

    console.log('queryString :: ' + queryString)
    return client.get(`/service/message/template?${queryString}`)
};

export const getResultList = ({page, year, month, startYear, startMonth, startDay, endYear, endMonth, endDay}) => {

    console.log('in getResultList');
    const queryString = qs.stringify({
        page, year, month, startYear, startMonth, startDay, endYear, endMonth, endDay
    });
    console.log("queryString :: " + queryString);
    return client.get(`service/message/result?${queryString}`)
};

