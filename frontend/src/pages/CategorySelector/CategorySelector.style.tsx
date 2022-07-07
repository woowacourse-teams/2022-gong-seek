import styled from '@emotion/styled';
import { CATEGORY_TYPE } from '@/constants/categoryType';
import { Link } from 'react-router-dom';

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	width: 100%;
	height: calc(100vh - 15rem);
	justify-content: center;
	align-items: center;
`;

export const CategoryButtonBox = styled.div`
	display: flex;
	gap: 1.5rem;
`;

export const CategoryButton = styled.button<{ categoryType: 'error' | 'discussion' }>`
	box-shadow: 0px 8px 24px ${({ theme }) => theme.boxShadows.secondary};
	background-color: ${({ categoryType }) => CATEGORY_TYPE[categoryType].color};
	border-color: transparent;
	border-radius: ${({ theme }) => theme.fonts.SIZE_010};
	color: white;
	width: ${({ theme }) => theme.fonts.SIZE_080};
	height: ${({ theme }) => theme.fonts.SIZE_080};
	font-size: ${({ theme }) => theme.fonts.SIZE_016};
	cursor: pointer;
	&:hover,
	&:active {
		background-color: ${({ categoryType }) => CATEGORY_TYPE[categoryType].hoverColor};
	}
`;

export const StyledLink = styled(Link)`
	text-decoration: none;
`;
