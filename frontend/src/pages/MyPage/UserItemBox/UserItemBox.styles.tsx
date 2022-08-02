import { keyframes } from "@emotion/react";
import styled from "@emotion/styled";

import { IoIosArrowBack } from "react-icons/io"; 

const arrowDownAnimation = keyframes`
    0%{
        transform: rotate(90deg);
    }
    50%{
        transform: rotate(0);
    }
    100%{
        transform: rotate(-90deg);
    }
`

const arrowUpAnimation = keyframes`
    0%{
        transform: rotate(-90deg);
    }
    50%{
        transform: rotate(0);
    }
    100%{
        transform: rotate(90deg);
    }
`


export const Container = styled.div`
    display: flex;
    flex-direction: column;
    
    width: 100%;
    height: fit-content;
`;

export const HeaderLine = styled.div`
    display: flex;
    
    justify-content: space-between;
    align-items: center;

    background-color: ${({theme}) => theme.colors.PURPLE_500};
    border-top-left-radius: ${({theme}) => theme.size.SIZE_010};
    border-top-right-radius: ${({theme}) => theme.size.SIZE_010};

    padding: ${({theme}) => theme.size.SIZE_010};
`;

export const SubTitle = styled.div`
    font-size:${({theme}) => theme.size.SIZE_014};
    color: ${({theme}) => theme.colors.WHITE}; 
`

export const DropDownButton = styled.button`
    width: fit-content;
    height: fit-content;

    border-style: none;
    background-color: transparent;
`;


export const DropDownImg = styled(IoIosArrowBack)<{isOpen: boolean}>`
    font-size: ${({theme}) => theme.size.SIZE_018};
    color: ${({theme}) => theme.colors.WHITE};
    transform: ${(props) => (props.isOpen ? 'rotate(90deg)' : 'rotate(-90deg)' )};
`;

export const ChildrenBox = styled.div`
    display: flex;
    height: fit-content;
    
    padding-bottom: ${({theme})=> theme.size.SIZE_020};

    border: ${({theme}) => theme.size.SIZE_001} solid ${({theme}) => theme.colors.GRAY_500};
    border-bottom-left-radius: ${({theme}) => theme.size.SIZE_010};
    border-bottom-right-radius: ${({theme}) => theme.size.SIZE_010};


`