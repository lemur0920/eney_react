import React from 'react';
import {TableBody} from "@material-ui/core";
import ServiceListItem from "./ServiceListItem";

const ServiceList = ({serviceList}) => {
    return (
        <TableBody>
            {serviceList.map((service,index) => (
                    <ServiceListItem service={service} key={service.reg_date} index={index +1} />
                ))}
        </TableBody>
    );
};

export default ServiceList;