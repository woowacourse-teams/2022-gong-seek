import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100%;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		gap: ${({ theme }) => theme.size.SIZE_100};
	}
`;

export const Title = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_018};
	color: ${({ theme }) => theme.colors.PURPLE_500};

	margin-bottom: ${({ theme }) => theme.size.SIZE_020};
	padding-left: ${({ theme }) => theme.size.SIZE_010};
`;

export const ContentContainer = styled.div`
	display: flex;
	flex-direction: column;

	gap: ${({ theme }) => theme.size.SIZE_040};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		gap: ${({ theme }) => theme.size.SIZE_056};
	}
`;

export const LinkTemporaryArticle = styled(Link)`
	width: 95%;
	text-decoration: none;
	text-align: right;
	padding-right: ${({ theme }) => theme.size.SIZE_020};

	color: ${({ theme }) => theme.colors.BLACK_500};

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 100%;
		text-align: center;
	}
`;
