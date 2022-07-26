import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

export const HeaderSection = styled.header`
	position: sticky;
	display: flex;

	top: 0;

	flex-direction: row;
	align-items: center;
	justify-content: space-around;

	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_056};

	background-color: ${({ theme }) => theme.colors.WHITE};

	padding-top: 1rem;
`;

export const LogoLink = styled.h1`
	font-weight: 800;
	font-size: ${({ theme }) => theme.size.SIZE_022};

	color: ${({ theme }) => theme.colors.PURPLE_500};
`;

export const SearchBarBox = styled.div`
	width: 60%;
`;

export const StyledLink = styled(Link)`
	text-decoration: none;
`;
