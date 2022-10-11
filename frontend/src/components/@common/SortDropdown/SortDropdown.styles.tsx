import { AiOutlineDown } from 'react-icons/ai';

import { css, keyframes } from '@emotion/react';
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

	width: fit-content;

	color: ${({ theme }) => theme.colors.BLACK_600};

	cursor: pointer;
`;

export const DropdownBox = styled.ul`
	display: flex;
	position: absolute;

	flex-direction: column;
	align-items: center;

	${({ theme }) => css`
		top: ${theme.size.SIZE_020};
		border-bottom-left-radius: ${theme.size.SIZE_006};
		border-bottom-right-radius: ${theme.size.SIZE_006};

		box-shadow: 0 ${theme.size.SIZE_008} ${theme.size.SIZE_024} ${theme.boxShadows.secondary};
	`}
`;

export const DropdownItem = styled.li<{ idx: number }>`
	width: 100%;
	text-align: center;
	margin: 0 auto;
	opacity: 0;

	overflow: hidden;

	animation-fill-mode: forwards;
	transform-origin: top center;
	cursor: pointer;

	${({ theme, idx }) => css`
		border-bottom: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};

		color: ${theme.colors.BLACK_600};
		background-color: ${theme.colors.WHITE};
		padding: ${theme.size.SIZE_008} ${theme.size.SIZE_004};
		animation: ${slideDown} 0.3s ease-in-out ${`${idx * 60}ms`};

		&:hover {
			color: ${theme.colors.PURPLE_500};
		}
	`};
`;

export const Container = styled.div`
	display: flex;
	position: relative;

	flex-direction: column;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_012};

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			font-size: ${theme.size.SIZE_014};
		}
	`}
`;

export const ArrowDown = styled(AiOutlineDown)``;
