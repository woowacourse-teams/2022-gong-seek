import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const PopUpAnimation = keyframes`
      0%{
        transform: translateY(0px);
        transform-origin: 50% 50%;
        box-shadow: none;
      }
      100%{
        transform: translateY(-100px);
        transform-origin: 50% 50%;
        box-shadow: 10px 5px 5px #9c9c9c;
      }
      
`;

export const Container = styled.section`
	display: flex;

	background-color: transparent;

	flex-direction: column;
	align-items: center;
	justify-content: flex-end;

	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;

	z-index: ${({ theme }) => theme.zIndex.SNACK_BAR_BACKGROUND};
`;

export const MessageBox = styled.div`
	width: 80%;
	height: fit-content;
	text-align: center;
	word-break: keep-all;

	${({ theme }) => css`
		padding: ${theme.size.SIZE_012};
		font-size: ${theme.size.SIZE_012};
		color: ${theme.colors.WHITE};

		border-radius: ${theme.size.SIZE_010};
		border-style: none;

		background-color: ${theme.colors.PURPLE_500};

		transform: translateY(-${theme.size.SIZE_100});
		box-shadow: ${theme.size.SIZE_010} ${theme.size.SIZE_004} ${theme.size.SIZE_004}${theme.colors.BLACK_300};

		animation: ${PopUpAnimation} 0.3s ease-in-out;

		@media (min-width: ${theme.breakpoints}) {
			width: fit-content;
			font-size: ${theme.size.SIZE_016};
		}
	`}
`;
