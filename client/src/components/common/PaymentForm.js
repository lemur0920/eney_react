import React from 'react';

const PaymentForm = ({payRequest}) => {

    console.log(payRequest)
    return (
        <form name="payment" method="post" acceptCharset="euc-kr">
            <input type="hidden"  name="SERVICE_ID" value={payRequest.service_ID} readOnly={true} hidden={true}/>
            <input type="hidden" name="ORDER_ID" value={payRequest.order_ID} readOnly={true} hidden={true}/>
            <input type="hidden"  name="ORDER_DATE" value={payRequest.order_DATE} readOnly={true} hidden={true}/>
            <input type="hidden"  name="USER_ID" value={payRequest.user_ID} readOnly={true} hidden={true}/>
            <input type="hidden"  name="USER_IP" value={payRequest.user_IP} readOnly={true} hidden={true}/>
            <input type="hidden"  name="ITEM_CODE" value={payRequest.item_CODE} readOnly={true} hidden={true}/>
            <input type="hidden"  name="ITEM_NAME" value={payRequest.item_NAME} readOnly={true} hidden={true}/>
            <input type="hidden"  name="AMOUNT" value={payRequest.amount} readOnly={true} hidden={true}/>
            <input type="hidden"  name="RETURN_URL" value={payRequest.return_URL} readOnly={true} hidden={true}/>
            <input type="hidden" name="USING_TYPE" value={payRequest.using_TYPE} readOnly={true} hidden={true}/>
            <input type="hidden"  name="CURRENCY" value={payRequest.currency} readOnly={true} hidden={true}/>
            <input type="hidden"  name="CARD_TYPE" value={payRequest.card_TYPE} readOnly={true} hidden={true}/>
            <input type="hidden"  name="INSTALLMENT_PERIOD" value={payRequest.installment_PERIOD} readOnly={true} hidden={true}/>
            {/*<input type="hidden" name="RESERVED1" value={payRequest.RESERVED1}/>*/}

        </form>
    );
};

export default PaymentForm;