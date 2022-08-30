import styled from '@emotion/styled';

export const Container = styled.div`
	position: absolute;
	top: ${({ theme }) => theme.size.SIZE_056};
	transform: translate(-50%);
	border-radius: ${({ theme }) => theme.size.SIZE_008};

	background-color: ${({ theme }) => theme.colors.WHITE};
	box-shadow: 1px 1px 3px ${({ theme }) => theme.colors.BLACK_400};
`;

export const DropdownItem = styled.div`
	width: ${({ theme }) => theme.size.SIZE_080};

	padding: ${({ theme }) => theme.size.SIZE_016};
	border-radius: ${({ theme }) => theme.size.SIZE_008};

	cursor: pointer;
	:hover {
		background-color: ${({ theme }) => theme.colors.BLUE_500};
		color: ${({ theme }) => theme.colors.WHITE};
	}
`;
