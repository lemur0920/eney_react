import React,{useEffect,useState} from 'react';
import Moment from "react-moment";

const CloudManagementItem = ({index,item,cx,start,stop,restart}) => {

    const [isStart, setIsStart] = useState(false);
    const [isStop, setIsStop] = useState(false);
    const [isReStart, setIsReStart] = useState(false);

    const [powerStatus, setPowerStatus] = useState("");

    useEffect(() => {
        const status = item.status
        const pStatus = item["OS-EXT-STS:power_state"];

        switch (status) {
            case "ACTIVE":
                setIsReStart(true);
                setIsStop(true);
                break;
            case "ERROR":
                setIsReStart(true);
                setIsStop(true);
                break;
            case "HARD_REBOOT":
                setIsReStart(true);
                break;
            case "PAUSED":
                setIsReStart(true);
                break;
            case "REBOOT":
                setIsReStart(true);
                break;
            case "SHUTOFF":
                setIsReStart(true);
                setIsStart(true);
                break;
            case "SUSPENDED":
                setIsReStart(true);
                break;
        }

        switch (pStatus) {
            case 0:
                setPowerStatus("NOSTATE");
                break;
            case 1:
                setPowerStatus("RUNNING");
                break;
            case 3:
                setPowerStatus("PAUSED");
                break;
            case 4:
                setPowerStatus("SHUTDOWN");
                break;
            case 6:
                setPowerStatus("CRASHED");
                break;
            case 7:
                setPowerStatus("SUSPENDED");
                break;

        }

    },[])

    return (
        <tr className={cx("active_tr")}>
            <td scope="col">{index+1}</td>
            <td scope="col">{item.name}</td>
            <td scope="col">{item.id}</td>
            <td scope="col">{item.status}</td>
            <td scope="col">{powerStatus}</td>
            <td scope="col">
                <Moment format="YYYY-MM-DD hh:mm">{item.created}</Moment>
            </td>
            <td>
                <button className={cx("btn")} onClick={() => {restart(item.id)}} hidden={!isReStart} disabled={!isReStart}>재시작</button>
                <button className={cx("btn")} onClick={() => {start(item.id)}} hidden={!isStart} disabled={!isStart}>시작</button>
                <button className={cx("btn")} onClick={() => {stop(item.id)}} hidden={!isStop} disabled={!isStop}>정지</button>
            </td>
        </tr>
    );
};

export default CloudManagementItem;