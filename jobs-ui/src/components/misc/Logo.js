import React from 'react'

function Logo(props) {
    const defaultLogoUrl = 'http://localhost:3000/jobs-portal-logo.svg'
    return (
        <div>
            <img src={props.logoUrl ? props.logoUrl : defaultLogoUrl} alt="" className="responsive-img" />
        </div>
    )
}

export default Logo