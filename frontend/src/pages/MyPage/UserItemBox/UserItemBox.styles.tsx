import { IoIosArrowBack } from 'react-icons/io';

import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

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
`;

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
`;

export const Container = styled.div`
	display: flex;
	flex-direction: column;

	width: 100%;
	height: fit-content;

	justify-content: center;
	align-items: center;
`;

export const HeaderLine = styled.div`
	display: flex;

	width: 90%;

	justify-content: space-between;
	align-items: center;

	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	border-top-left-radius: ${({ theme }) => theme.size.SIZE_010};
	border-top-right-radius: ${({ theme }) => theme.size.SIZE_010};

	padding: ${({ theme }) => theme.size.SIZE_010};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 50%;
	}
`;

export const SubTitle = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_014};
	color: ${({ theme }) => theme.colors.WHITE};
`;

export const DropDownButton = styled.button`
	width: fit-content;
	height: fit-content;

	border-style: none;
	background-color: transparent;
`;

export const DropDownImg = styled(IoIosArrowBack)<{ isopen: string }>`
	font-size: ${({ theme }) => theme.size.SIZE_018};
	color: ${({ theme }) => theme.colors.WHITE};
	transform: ${(props) => (props.isopen === 'true' ? 'rotate(90deg)' : 'rotate(-90deg)')};
`;

export const ChildrenBox = styled.div`
	display: flex;

	width: 90%;

	flex-direction: column;
	justify-content: center;
	align-items: center;

	height: fit-content;

	padding: ${({ theme }) => theme.size.SIZE_010};

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	border-bottom-left-radius: ${({ theme }) => theme.size.SIZE_010};
	border-bottom-right-radius: ${({ theme }) => theme.size.SIZE_010};

	gap: ${({ theme }) => theme.size.SIZE_020};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 50%;
	}
`;
