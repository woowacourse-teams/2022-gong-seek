import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

export const HeaderSection = styled.header`
	position: sticky;

	top: 0;
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-around;
	padding-top: 1rem;
	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_056};
	background-color: ${({ theme }) => theme.colors.WHITE};
`;

export const LogoLink = styled.h1`
	font-weight: 800;
	color: ${({ theme }) => theme.colors.PURPLE_500};
	font-size: ${({ theme }) => theme.size.SIZE_022};
`;

export const SearchBarBox = styled.div`
	width: 60%;
`;

export const StyledLink = styled(Link)`
	text-decoration: none;
`;
