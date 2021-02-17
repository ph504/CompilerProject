.data
	d1: .float 0.0				# double decl.
	arthDoubleStackPtr: .word 268501152
	doubleConst0: .float 3.1415
	newLine: .asciiz "\n"
.text
main:

	l.s $f4 , doubleConst0

	la $t0 , 268501152
	s.s $f4, 0($t0)
	addi $t0, $t0, 4				# push double mips.

	sub $t0, $t0, 4
	l.s $f4, 0($t0)				# pop double mips.

	add.s $f6, $f30, $f4				# double assignment
	la $t5, d1
	s.s $f6, 0($t5)

	s.s $f6, 0($t0)
	addi $t0, $t0, 4				# push double mips.

	la $t5, d1				# rval fetch double op.
	l.s $f4, 0($t5) 
	s.s $f4, 0($t0)
	addi $t0, $t0, 4				# push double mips.


	sub $t0, $t0, 4
	l.s $f4, 0($t0)				# pop double mips.

	add.s $f12, $f31, $f4
	li $v0, 2
	syscall
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	li $v0, 10
	syscall

