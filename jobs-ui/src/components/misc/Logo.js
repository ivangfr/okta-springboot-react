import React from 'react'

function Logo({ logoUrl }) {
    const defaultLogoUrl = '/jobs-portal-logo.svg'
    return (
        <div>
            <img src={logoUrl ? logoUrl : defaultLogoUrl} alt="" className="responsive-img" />
        </div>
    )
}

export default Logo