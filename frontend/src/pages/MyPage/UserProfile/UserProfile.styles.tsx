import styled from "@emotion/styled";

export const Container = styled.div`
    display: flex;

    width: 100%;

    flex-direction: column;
    justify-content: center;
    align-items: center;
    
    gap: ${({theme}) => theme.size.SIZE_020};
`;

export const UserProfile = styled.img`
    width: ${({theme}) => theme.size.SIZE_050};
    height: ${({theme}) => theme.size.SIZE_050};

    border-radius: 50%;

    object-fit: cover;
    object-position: center;
`

export const UserName = styled.div`
    text-align: center;
    font-size: ${({theme}) => theme.size.SIZE_014};
`