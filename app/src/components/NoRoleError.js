import {useLocation, useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";

function NoRoleError(){

    const [params, setParams] = useSearchParams();

    return (
        <div>
            {`I am sorry, you need role ${params.get("role")} for that action`}
        </div>
    )
}

export default NoRoleError

