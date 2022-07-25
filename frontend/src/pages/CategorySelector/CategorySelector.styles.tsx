import { Link } from 'react-router-dom';

import { CATEGORY_TYPE } from '@/constants/categoryType';
import styled from '@emotion/styled';

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

export const CategoryButton = styled.button<{ categoryType: 'question' | 'discussion' }>`
	box-shadow: 0px 8px 24px ${({ theme }) => theme.boxShadows.secondary};
	background-color: ${({ categoryType }) => CATEGORY_TYPE[categoryType].color};
	border-color: transparent;
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	color: white;
	width: ${({ theme }) => theme.size.SIZE_080};
	height: ${({ theme }) => theme.size.SIZE_080};
	font-size: ${({ theme }) => theme.size.SIZE_016};
	cursor: pointer;
	&:hover,
	&:active {
		background-color: ${({ categoryType }) => CATEGORY_TYPE[categoryType].hoverColor};
		transform: scale(1.02);
	}
`;

export const StyledLink = styled(Link)`
	text-decoration: none;
`;
