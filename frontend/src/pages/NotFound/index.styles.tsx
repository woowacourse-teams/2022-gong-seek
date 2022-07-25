import styled from '@emotion/styled';
import { Link } from 'react-router-dom';

export const Container = styled.div`
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	height: 68vh;
`;

export const ErrorContent = styled.p`
	font-size: ${({ theme }) => theme.size.SIZE_020};
	line-height: ${({ theme }) => theme.size.SIZE_040};
	margin-top: ${({ theme }) => theme.size.SIZE_056};
	font-weight: 400;
	text-align: center;
`;

export const LogoImage = styled.img`
	width: ${({ theme }) => theme.size.SIZE_090};
	height: ${({ theme }) => theme.size.SIZE_090};
`;

export const NavigateLink = styled(Link)`
	text-decoration: none;
	margin-top: ${({ theme }) => theme.size.SIZE_030};
	border: 3px solid ${({ theme }) => theme.colors.PURPLE_500};
	padding: ${({ theme }) => theme.size.SIZE_010} ${({ theme }) => theme.size.SIZE_024};
	font-weight: 600;

	&:hover,
	&:active {
		color: white;
		background-color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;
