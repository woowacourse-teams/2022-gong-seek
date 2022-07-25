import { MdOutlineKeyboardArrowDown } from 'react-icons/md';

import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

export const slideDown = keyframes`
0% { 
  opacity: 0;
  transform: translateY(-60px);
}

100% {
  opacity: 1;
  transform: translateY(0);
}

`;
export const SortBox = styled.div`
	display: flex;
	align-items: center;
	justify-content: center;
	color: ${({ theme }) => theme.colors.BLACK_600};
	cursor: pointer;
`;

export const DropdownBox = styled.ul`
	position: absolute;
	top: ${({ theme }) => theme.size.SIZE_016};
	display: flex;
	flex-direction: column;
	align-items: center;
	box-shadow: 0 8px 24px ${({ theme }) => theme.boxShadows.secondary};
	border-bottom-left-radius: ${({ theme }) => theme.size.SIZE_006};
	border-bottom-right-radius: ${({ theme }) => theme.size.SIZE_006};
`;

export const DropdownItem = styled.li<{ idx: number }>`
	color: ${({ theme }) => theme.colors.BLACK_600};
	padding: ${({ theme }) => theme.size.SIZE_008} ${({ theme }) => theme.size.SIZE_012}
		${({ theme }) => theme.size.SIZE_008} 0;
	background-color: ${({ theme }) => theme.colors.WHITE};
	opacity: 0;
	cursor: pointer;
	text-align: center;
	&:hover {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
	overflow: hidden;
	border-bottom: 1px solid ${({ theme }) => theme.colors.GRAY_500};
	animation: ${slideDown} 0.3s ease-in-out ${({ idx }) => `${idx * 60}ms`};
	animation-fill-mode: forwards;
	transform-origin: top center;
`;

export const Container = styled.div`
	position: relative;
	display: flex;
	flex-direction: column;
	font-size: ${({ theme }) => theme.size.SIZE_012};
`;

export const ArrowDown = styled(MdOutlineKeyboardArrowDown)``;
