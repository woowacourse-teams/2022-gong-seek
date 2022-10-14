import { Link } from 'react-router-dom';

import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const searchOpenMaxAnimation = keyframes`
	0% {
		width: 40%;
	}
	10%, 40%{
		width: 45%;
	}
	40%, 80% {
		width: 55%;
	}
	80%, 100%{
		width: 60%;
	}
`;

const searchOpenMidAnimation = keyframes`
	0% {
		width: 40%;
	}
	10%, 40%{
		width: 55%;
	}
	40%, 80% {
		width: 65%;
	}
	80%, 100%{
		width: 80%;
	}
`;

export const Container = styled.div<{ active: boolean }>`
	position: sticky;
	display: flex;

	top: 0;

	flex-direction: column;

	width: 100%;
	height: fit-content;

	transition-property: opacity, transform;
	transition-duration: 500ms, 500ms;

	${({ theme, active }) => css`
		z-index: ${theme.zIndex.HEADER};

		border-bottom: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};
		margin-bottom: ${theme.size.SIZE_030};

		${active
			? css`
					transform: tranlate(0);
					opacity: 1;
			  `
			: css`
					transform: translateY(-200px);
					opacity: 0;
			  `}
	`}
`;

export const HeaderSection = styled.header`
	display: flex;

	flex-direction: row;
	align-items: center;
	justify-content: space-around;

	width: 100%;

	${({ theme }) => css`
		height: ${theme.size.SIZE_056};

		background-color: ${theme.colors.WHITE};

		padding: ${theme.size.SIZE_016} 0;

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: calc(100% - ${theme.size.SIZE_160} * 2);
			padding: ${theme.size.SIZE_016} ${theme.size.SIZE_160};
			justify-content: space-between;
		}

		@media (min-width: ${theme.breakpoints.DESKTOP_MIDDLE}) {
			width: calc(100% - ${theme.size.SIZE_080} * 2);
			padding: ${theme.size.SIZE_016} ${theme.size.SIZE_080};
			justify-content: space-between;
		}
	`}
`;

export const LogoLink = styled.h1`
	font-weight: 800;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_022};
		color: ${theme.colors.PURPLE_500};
	`}
`;

export const LogoImage = styled.img`
	${({ theme }) => css`
		width: ${theme.size.SIZE_030};
		height: ${theme.size.SIZE_030};
	`}
`;

export const SearchBarBox = styled.div`
	width: 60%;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		min-width: 40%;
	}
`;

export const SearchOpenBox = styled.div`
	width: 80%;
	animation: ${searchOpenMidAnimation} 0.3s ease-in-out;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		width: 60%;
		animation: ${searchOpenMaxAnimation} 0.3s ease-in-out;
	}
`;

export const StyledLink = styled(Link)`
	text-decoration: none;
`;

export const NavBar = styled.nav`
	display: none;
	visibility: hidden;

	${({ theme }) => css`
		padding: 0 ${theme.size.SIZE_160};

		background-color: ${theme.colors.WHITE};

		@media (min-width: ${theme.breakpoints.DESKTOP_SMALL}) {
			width: calc(100% - ${theme.size.SIZE_080} * 2);
			display: flex;
			visibility: visible;
			padding: 0 ${theme.size.SIZE_080};
			margin: 0 auto;
			justify-content: space-around;
		}
	`}
`;

export const NavBarItemBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_024};
	width: fit-content;
`;

export const NavBarItem = styled(Link)`
	width: fit-content;
	text-decoration: none;

	${({ theme }) => css`
		color: ${theme.colors.BLACK_600};
		padding: ${theme.size.SIZE_016} 0;

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
		}
	`}
`;

export const ProfileIconBox = styled.div`
	width: fit-content;
	margin: ${({ theme }) => theme.size.SIZE_004} ${({ theme }) => theme.size.SIZE_040} 0 auto;
`;

export const LoginIn = styled(Link)`
	width: fit-content;
	text-decoration: none;

	background-color: transparent;

	${({ theme }) => css`
		color: ${theme.colors.BLACK_600};

		margin: 0 ${theme.size.SIZE_032} 0 auto;
		padding: ${theme.size.SIZE_016};

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
		}
	`}
`;
