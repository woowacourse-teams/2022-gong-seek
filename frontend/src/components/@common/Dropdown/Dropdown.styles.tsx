import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
	position: absolute;

	transform: translate(-35%);

	${({ theme }) => css`
		top: ${theme.size.SIZE_056};
		border-radius: ${theme.size.SIZE_008};

		background-color: ${theme.colors.WHITE};
		box-shadow: ${theme.size.SIZE_001} ${theme.size.SIZE_001} ${theme.size.SIZE_003}
			${theme.colors.BLACK_400};
	`}
`;

export const DropdownItem = styled.div`
	${({ theme }) => css`
		width: ${theme.size.SIZE_060};
		font-size: ${theme.size.SIZE_012};
		padding: ${theme.size.SIZE_016};
		border-radius: ${theme.size.SIZE_008};

		&:hover {
			background-color: ${theme.colors.BLUE_500};
			color: ${theme.colors.WHITE};
			cursor: pointer;
		}
	`}
`;
