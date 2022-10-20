import { Link } from 'react-router-dom';

import { CATEGORY_TYPE } from '@/constants/categoryType';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	position: relative;

	justify-content: center;
	align-items: center;

	width: 100%;
	height: 60vh;
`;

export const CategoryButtonBox = styled.div`
	display: flex;

	gap: 1.5rem;
`;

export const CategoryButton = styled.button<{ categoryType: 'question' | 'discussion' }>`
	border-color: transparent;

	cursor: pointer;

	${({ theme, categoryType }) => css`
		width: ${theme.size.SIZE_080};
		height: ${theme.size.SIZE_080};

		border-radius: ${theme.size.SIZE_010};

		font-size: ${theme.size.SIZE_016};

		color: ${theme.colors.WHITE};
		box-shadow: 0 ${theme.size.SIZE_008} ${theme.size.SIZE_024} ${theme.boxShadows.secondary};
		background-color: ${CATEGORY_TYPE[categoryType].color};

		&:hover,
		&:active {
			background-color: ${CATEGORY_TYPE[categoryType].hoverColor};
			transform: scale(1.02);
		}
	`}
`;

export const StyledLink = styled(Link)`
	text-decoration: none;
`;
