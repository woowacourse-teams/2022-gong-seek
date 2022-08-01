import { Link } from 'react-router-dom';

import styled from '@emotion/styled';
import { keyframes } from '@emotion/react';


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
`


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
`

export const Container = styled.div`
	position: sticky;
	display: flex;

	top: 0;

	flex-direction: column;

	width: 100%;
	height: fit-content;

	z-index: ${({ theme }) => theme.zIndex.HEADER};

	border-bottom: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	margin-bottom: ${({ theme }) => theme.size.SIZE_030};
`;

export const HeaderSection = styled.header`
	display: flex;

	flex-direction: row;
	align-items: center;
	justify-content: space-around;

	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_056};

	background-color: ${({ theme }) => theme.colors.WHITE};

	padding: 1rem 0;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: calc(100% - ${({ theme }) => theme.size.SIZE_160} * 2);
		padding: 1rem ${({ theme }) => theme.size.SIZE_160};
		justify-content: space-between;
	}
`;


export const LogoLink = styled.h1`
	font-weight: 800;
	font-size: ${({ theme }) => theme.size.SIZE_022};

	color: ${({ theme }) => theme.colors.PURPLE_500};
`;

export const LogoImage = styled.img`
	width: ${({theme}) => theme.size.SIZE_030};
	height: ${({theme}) => theme.size.SIZE_030};
`

export const SearchBarBox = styled.div`
	width: 60%;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		min-width: 40%;
	}
`;

export const SearchOpenBox = styled.div`
	width: 80%;
	animation: ${searchOpenMidAnimation} 0.3s ease-in-out;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 60%;
		animation: ${searchOpenMaxAnimation} 0.3s ease-in-out;
	}
`

export const StyledLink = styled(Link)`
	text-decoration: none;
`;

export const NavBar = styled.nav`
	display: none;
	visibility: hidden;

	padding-left: ${({ theme }) => theme.size.SIZE_160};

	background-color: ${({ theme }) => theme.colors.WHITE};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		display: flex;
		visibility: visible;

		gap: ${({ theme }) => theme.size.SIZE_024};
	}
`;

export const NavBarItem = styled(Link)`
	text-decoration: none;

	color: ${({ theme }) => theme.colors.BLACK_600};

	padding: ${({ theme }) => theme.size.SIZE_016} 0;

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

export const LogOutItem = styled.button`
	border: none;

	background-color: transparent;
	color: ${({ theme }) => theme.colors.BLACK_600};

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;


