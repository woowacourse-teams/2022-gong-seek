import { AiOutlineLeft } from 'react-icons/ai';

import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const showSlider = keyframes`
	0%{
		transform: translate3d(500px, 0,0);
	}100%{
		transform: translate3d(0,0,0);
	}
`;

export const MenuBox = styled.div`
	display: flex;
	position: fixed;

	top: 0;
	right: 0;

	flex-direction: column;
	justify-content: flex-start;
	align-items: center;

	width: 70%;
	height: 100vh;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_018};
		padding-top: ${theme.size.SIZE_030};

		border-radius: ${theme.size.SIZE_010};

		background-color: ${theme.colors.WHITE};

		z-index: ${theme.zIndex.MENU_SLIDER_CONTENT};

		animation: ${showSlider} 0.2s ease-in-out;

		@media (min-width: ${theme.breakpoints.DESKTOP_SMALL}) {
			display: none;
		}
	`}
`;

export const Header = styled.header`
	display: flex;

	justify-content: flex-start;

	width: 80%;

	margin-bottom: ${({ theme }) => theme.size.SIZE_050};
`;

export const BackButtonBox = styled.button`
	border-style: none;

	background-color: transparent;

	margin-left: -${({ theme }) => theme.size.SIZE_010};

	cursor: pointer;
`;

export const BackButton = styled(AiOutlineLeft)`
	${({ theme }) => css`
	font-size: ${theme.size.SIZE_024};

	&:hover,
	&:active {
		color: $ {theme.colors.PURPLE_500};
	}
	`};
`;

export const LinkBox = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_040};

	width: 80%;
`;

export const LinkItem = styled.div`
	cursor: pointer;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_016};

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
			font-weight: 600;
		}
	`}
`;
